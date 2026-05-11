package holdec.jarmo.arm64;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * JMH benchmark for the ARM64 disassembler.
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 3)
@Measurement(iterations = 5, time = 3)
@Fork(2)
public class DisasmBenchmark {

    private static final int POOL_SIZE = 1 << 20; // 1,048,576 — power of 2 for fast masking

    private ArmDisasmDecoder decoder;
    private int[] validOpcodes;
    private int index;

    private int[] randomOpcodes;

    @Setup(Level.Trial)
    public void setup() {
        decoder = new ArmDisasmDecoder();
        validOpcodes = new int[POOL_SIZE];
        randomOpcodes = new int[POOL_SIZE];

        System.out.println("\nBuilding valid-opcode pool (" + POOL_SIZE + " opcodes)...");
        Random rng = new Random(42L);
        int filled = 0;
        int total = 0;
        while (filled < POOL_SIZE) {
            int opcode = rng.nextInt();
            total++;
            AsmStatement s = decoder.decode(filled * 4L, opcode);
            if (!".inst".equals(s.opcode)) {
                validOpcodes[filled++] = opcode;
            }
        }
        System.out.printf("Pool ready: %,d valid out of %,d sampled (%.1f%% valid)%n",
                POOL_SIZE, total, 100.0 * POOL_SIZE / total);

        // Random pool: raw random integers, ~69% will hit markAsUnknown.
        Random rng2 = new Random(99L);
        for (int i = 0; i < POOL_SIZE; i++) {
            randomOpcodes[i] = rng2.nextInt();
        }
        index = 0;
    }

    /**
     * Full pipeline: decode a 32-bit opcode and format it as a string.
     * This is the main end-to-end benchmark.
     */
    @Benchmark
    public String decodeAndFormat() {
        int i = index++ & (POOL_SIZE - 1);
        return decoder.decode((long) i * 4, validOpcodes[i]).format();
    }

    /**
     * Decode only — no string formatting.
     * Forces all arg1-arg5 fields to be computed (by accumulating their hashCodes)
     * so the JIT cannot eliminate argument string-building as dead code.
     * This is the primary benchmark for the real use case: consumer reads individual
     * arg fields, never calls format().
     */
    @Benchmark
    public int decodeOnly() {
        int i = index++ & (POOL_SIZE - 1);
        AsmStatement s = decoder.decode((long) i * 4, validOpcodes[i]);
        int h = s.opcode.hashCode();
        if (s.arg1 != null) h ^= s.arg1.hashCode();
        if (s.arg2 != null) h ^= s.arg2.hashCode();
        if (s.arg3 != null) h ^= s.arg3.hashCode();
        if (s.arg4 != null) h ^= s.arg4.hashCode();
        if (s.arg5 != null) h ^= s.arg5.hashCode();
        return h;
    }

    /**
     * Random opcodes — ~69% hit markAsUnknown (.inst fallback).
     * Exercises the invalid-instruction path.
     */
    @Benchmark
    public String decodeRandom() {
        int i = index++ & (POOL_SIZE - 1);
        return decoder.decode((long) i * 4, randomOpcodes[i]).format();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(DisasmBenchmark.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
