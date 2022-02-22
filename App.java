package jsonFormater;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * @author Tatek Ahmed on 2/17/2022
 **/

public class App {
    public static void main(String[] args) throws IllegalAccessException {
        FavoriteSports favoriteSports = new FavoriteSports(new String[]{"Bicycle","Football","Tennis"});
        Address address = new Address("Main Street", (short) 1);
        Company company = new Company("Udemy","San Francisco", new Address("Harrison Street",(short) 600));
        Person person = new Person("Tatek",true,20,100.555f,address,company,FavoriteSports.sports);

        int [] oneDimensionalArray = {1,2};
        int [][] twoDimensionalArray = {{1,2},{3,4}};

        formatArrayValue(oneDimensionalArray);
        System.out.println();
        formatArrayValue(FavoriteSports.sports);
        System.out.println();
        System.out.println(objectToJson(address,0));
        System.out.println(objectToJson(company,0));
        System.out.println(objectToJson(person,0));
    }
    private static String objectToJson(Object instance,int indentSize) throws IllegalAccessException {
        Field [] fields = instance.getClass().getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(indent(indentSize));
        stringBuilder.append("{");
        stringBuilder.append("\n");

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            if (field.isSynthetic())
                continue;

            stringBuilder.append(indent(indentSize +1));
            stringBuilder.append(formatStringValue(field.getName()));
            stringBuilder.append(":");

            if (field.getType().isPrimitive())
                stringBuilder.append(formatPrimitiveValue(field.get(instance),field.getType()));
            else if(field.getType().equals(String.class))
                stringBuilder.append(formatStringValue(field.get(instance).toString()));
            else if (field.getType().isArray())
                stringBuilder.append(arrayToJson(field.get(instance),indentSize +1));
            else
                stringBuilder.append(objectToJson(field.get(instance), indentSize + 1));
            if (i != fields.length -1) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\n");
        }

        stringBuilder.append(indent(indentSize));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private static String formatArrayValue(Object arrayObject) {
        int arrayLength = Array.getLength(arrayObject);

        System.out.print("[");

        for (int i = 0; i < arrayLength; i++) {
            Object element = Array.get(arrayObject,i);
            if (element.getClass().isArray())
                formatArrayValue(element);
            else
                System.out.print(element);

            if (i!= arrayLength -1)
                System.out.print(",");
        }
        System.out.print("]");
        return null;
    }

    private static String arrayToJson(Object arrayInstance, int indentSize) throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();
        int arrayLength = Array.getLength(arrayInstance);

        Class <?> componentType = arrayInstance.getClass().getComponentType();

        stringBuilder.append("[");
        stringBuilder.append("\n");

        for (int i = 0; i < arrayLength; i++) {
            Object element = Array.get(arrayInstance,i);
            if(componentType.isPrimitive()) {
                stringBuilder.append(indent(indentSize+1));
                stringBuilder.append(formatPrimitiveValue(element, componentType));
            }
//            else if (element.getClass().isArray())
//                formatArrayValue(element);
            else if (componentType.equals(String.class)) {
                stringBuilder.append(indent(indentSize+1));
                stringBuilder.append(formatStringValue(element.toString()));
            }

            if (i!= arrayLength -1)
                stringBuilder.append(",");

            stringBuilder.append("\n");
        }

        stringBuilder.append(indent(indentSize));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private static String indent(int indentSize){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < indentSize; i++) {
            stringBuilder.append("\t");
        }
        return stringBuilder.toString();
    }

    private static String formatPrimitiveValue(Object instance, Class<?> type) throws IllegalAccessException {
        if (type.equals(boolean.class)
                || type.equals(int.class)
                || type.equals(long.class)
                || type.equals(short.class)){
            return instance.toString();
        }else if (type.equals(double.class)|| type.equals(float.class))
            return String.format("%.02f", instance);
        throw new RuntimeException(String.format("Type : %s is unsupported", type.getName()));
    }

    private static String formatStringValue(String value){
        return String.format("\"%s\"",value);
    }
}
