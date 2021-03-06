package japicmp.util;

import java.util.LinkedList;
import java.util.List;

public class SignatureParser {
    private List<String> parameters = new LinkedList<String>();
    private String returnType = "void";

    public void parse(String signature) {
        int parenthesisCloseIndex = signature.indexOf(')');
        if(parenthesisCloseIndex > -1) {
            parseParameters(signature, parenthesisCloseIndex);
            parseReturnValue(signature, parenthesisCloseIndex);
        }
    }

    private void parseReturnValue(String signature, int parenthesisCloseIndex) {
        String retValPart = signature.substring(parenthesisCloseIndex+1);
        List<String> retValTypes = parseTypes(retValPart);
        returnType = retValTypes.get(0);
    }

    private void parseParameters(String signature, int parenthesisCloseIndex) {
        String paramPart = signature.substring(1, parenthesisCloseIndex);
        List<String> paramTypes = parseTypes(paramPart);
        parameters.clear();
        parameters.addAll(paramTypes);
    }

    public List<String> parseTypes(String paramPart) {
        List<String> types = new LinkedList<String>();
        boolean arrayNotation = false;
        for(int i=0; i<paramPart.length(); i++) {
            char c = paramPart.charAt(i);
            String type = "void";
            switch(c) {
                case 'Z':
                    type = "boolean";
                    break;
                case 'B':
                    type = "byte";
                    break;
                case 'C':
                    type = "char";
                    break;
                case 'S':
                    type = "short";
                    break;
                case 'I':
                    type = "int";
                    break;
                case 'J':
                    type = "long";
                    break;
                case 'F':
                    type = "float";
                    break;
                case 'D':
                    type = "double";
                    break;
                case 'V':
                    type = "void";
                    break;
                case '[':
                    arrayNotation = true;
                    continue;
                case 'L':
                    StringBuilder fqn = new StringBuilder();
                    i++;
                    while(i<paramPart.length()) {
                        c = paramPart.charAt(i);
                        if(c == ';') {
                            break;
                        } else if(c == '/') {
                            fqn.append('.');
                        } else {
                            fqn.append(c);
                        }
                        i++;
                    }
                    type = fqn.toString();
                    break;
                default:
                    throw new IllegalStateException("Unknown type signature: '"+c+"'");
            }
            if(arrayNotation) {
                type += "[]";
                arrayNotation = false;
            }
            types.add(type);
        }
        return types;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public String getReturnType() {
        return returnType;
    }
}
