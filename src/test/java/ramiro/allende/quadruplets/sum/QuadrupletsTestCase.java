package ramiro.allende.quadruplets.sum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class QuadrupletsTestCase {
    private final int target;
    private final int[] array;
    private final List<Integer[]> quadruplets;

    private QuadrupletsTestCase(QuadrupletsTestCaseBuilder builder) {
        this.target = builder.target;
        this.array = builder.array;
        this.quadruplets = builder.quadruplets;
    }

    public int getTarget() {
        return target;
    }

    public int[] getArray() {
        return array;
    }

    public List<Integer[]> getQuadruplets() {
        return quadruplets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuadrupletsTestCase that = (QuadrupletsTestCase) o;
        return target == that.target
                && Arrays.equals(array, that.array)

                && new HashSet<>(quadruplets).equals(new HashSet<>(that.quadruplets));
    }

    public static QuadrupletsTestCaseBuilder builder() {
        return new QuadrupletsTestCaseBuilder();
    }

    public static class QuadrupletsTestCaseBuilder {
        private int target;
        private int[] array;
        private final List<Integer[]> quadruplets;

        public QuadrupletsTestCaseBuilder() {
            this.quadruplets = new LinkedList<>();
        }

        public QuadrupletsTestCaseBuilder target(int target) {
            this.target = target;
            return this;
        }

        public QuadrupletsTestCaseBuilder array(int... array) {
            this.array = array;
            return this;
        }

        public QuadrupletsTestCaseBuilder quadruplet(int... quadruplet) {
            quadruplets.add(Arrays.stream(quadruplet).boxed().toArray(Integer[]::new));
            return this;
        }

        public QuadrupletsTestCase build() {
            return new QuadrupletsTestCase(this);
        }
    }
}
