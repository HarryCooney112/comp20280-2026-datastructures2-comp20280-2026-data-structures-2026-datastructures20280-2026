package project20280.stacksqueues;

class BracketChecker {
    private final String input;

    public BracketChecker(String in) {
        input = in;
    }

    public void check() {
        // TODO

        ArrayStack<Character> x = new ArrayStack<Character>();
        for (Character c: input.toCharArray()) {
            if (c == '(') {
                x.push(')');
            } else if (c == '[') {
                x.push(']');
            } else if (c == '{') {
                x.push('}');
            }
            if (c == ')' || c == '}' || c == ']') {
                if (x.pop() == null) {
                    System.out.println("Invalid");
                    return;
                }
            }
        }
        if (x.isEmpty()) {
            System.out.println("Valid");
        } else {
            System.out.println("Invalid");
        }
    }

    public static void main(String[] args) {
        String[] inputs = {
                "[]]()()", // not correct
                "c[d]", // correct\n" +
                "a{b[c]d}e", // correct\n" +
                "a{b(c]d}e", // not correct; ] doesn't match (\n" +
                "a[b{c}d]e}", // not correct; nothing matches final }\n" +
                "a{b(c) ", // // not correct; Nothing matches opening {
        };

        for (String input : inputs) {
            BracketChecker checker = new BracketChecker(input);
            System.out.println("checking: " + input);
            checker.check();
        }
    }
}