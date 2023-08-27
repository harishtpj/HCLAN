package com.harishlangs.hclan.std;

import java.util.List;

import com.harishlangs.hclan.*;

public class HclanMath extends HclanClass {
    public HclanMath() {
        super(null, null, null, null);
    }

    @Override
    protected Object get(Token name) {
        switch (name.lexeme) {
            case "e":
                return Math.E;
            case "pi":
                return Math.PI;
            case "abs":
                return funAbs(name);
            case "acos":
                return funAcos(name);
            case "asin":
                return funAsin(name);
            case "cbrt":
                return funCbrt(name);
            case "ceil":
                return funCeil(name);
            case "cos":
                return funCos(name);
            case "exp":
                return funExp(name);
            case "floor":
                return funFloor(name);
            case "hypot":
                return funHypot(name);
            case "ln":
                return funLn(name);
            case "log":
                return funLog(name);
            case "log10":
                return funLog10(name);
            case "max":
                return funMax(name);
            case "min":
                return funMin(name);
            case "pow":
                return funPow(name);
            case "random":
                return funRandom();
            case "round":
                return funRound(name);
            case "sin":
                return funSin(name);
            case "sqrt":
                return funSqrt(name);
            case "tan":
                return funTan(name);
            case "toDegrees":
                return funToDegrees(name);
            case "toRadians":
                return funToRadians(name);
            default:
                throw new RuntimeError(name, String.format("Undefined Property: %s for Module 'Math'", name.lexeme));
        }
    }

    private Object funAbs(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.abs((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.abs>"; }
        };
    }

    private Object funAcos(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.acos((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.acos>"; }
        };
    }

    private Object funAsin(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.asin((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.asin>"; }
        };
    }

    private Object funCbrt(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.cbrt((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.cbrt>"; }
        };
    }

    private Object funCeil(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.ceil((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.ceil>"; }
        };
    }

    private Object funCos(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.cos((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.cos>"; }
        };
    }

    private Object funExp(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.exp((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.exp>"; }
        };
    }

    private Object funFloor(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.floor((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.floor>"; }
        };
    }

    private Object funHypot(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 2; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                HclanUtils.checkNumber(name, arguments.get(1));
                return Math.hypot((Double)arguments.get(0), (Double)arguments.get(1));
            }

            @Override
            public String toString() { return "<Module Math.hypot>"; }
        };
    }

    private Object funLn(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.log((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.ln>"; }
        };
    }

    private Object funLog(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 2; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                Double base = (Double)arguments.get(0);
                Double num = (Double)arguments.get(1);

                return Math.log10(num) / Math.log10(base);
            }

            @Override
            public String toString() { return "<Module Math.log>"; }
        };
    }

    private Object funLog10(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.log10((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.log10>"; }
        };
    }

    private Object funMax(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 2; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                HclanUtils.checkNumber(name, arguments.get(1));
                return Math.max((Double)arguments.get(0), (Double)arguments.get(1));
            }

            @Override
            public String toString() { return "<Module Math.max>"; }
        };
    }

    private Object funMin(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 2; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                HclanUtils.checkNumber(name, arguments.get(1));
                return Math.min((Double)arguments.get(0), (Double)arguments.get(1));
            }

            @Override
            public String toString() { return "<Module Math.min>"; }
        };
    }

    private Object funPow(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 2; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                HclanUtils.checkNumber(name, arguments.get(1));
                return Math.pow((Double)arguments.get(0), (Double)arguments.get(1));
            }

            @Override
            public String toString() { return "<Module Math.pow>"; }
        };
    }

    private Object funRandom() {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 0; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return Math.random();
            }

            @Override
            public String toString() { return "<Module Math.random>"; }
        };
    }

    private Object funRound(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.round((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.round>"; }
        };
    }

    private Object funSin(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.sin((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.sin>"; }
        };
    }

    private Object funSqrt(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.sqrt((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.sqrt>"; }
        };
    }

    private Object funTan(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.tan((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.tan>"; }
        };
    }

    private Object funToDegrees(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.toDegrees((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.toDegrees>"; }
        };
    }

    private Object funToRadians(Token name) {
        return new HclanCallable() {
            @Override
            public boolean isVaArg() { return false; }

            @Override
            public int arity() { return 1; }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                HclanUtils.checkNumber(name, arguments.get(0));
                return Math.toRadians((Double)arguments.get(0));
            }

            @Override
            public String toString() { return "<Module Math.toRadians>"; }
        };
    }
}
