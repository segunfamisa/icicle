package com.segunfamisa.icicle.processor;

import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor6;
import javax.lang.model.util.Types;

class Bundleables {
    static final TypeName BUNDLE = ClassName.get("android.os", "Bundle");
    static final TypeName IBINDER = ClassName.get("android.os", "IBinder");
    static final TypeName BOOLEAN = ClassName.BOOLEAN;
    static final TypeName BOOLEAN_ARRAY = ArrayTypeName.of(BOOLEAN);
    static final TypeName BYTE = ClassName.BYTE;
    static final TypeName BYTE_ARRAY = ArrayTypeName.of(byte.class);
    static final TypeName CHAR = ClassName.CHAR;
    static final TypeName CHAR_ARRAY = ArrayTypeName.of(char.class);
    static final TypeName CHARSEQUENCE = ClassName.get("java.lang", "CharSequence");
    static final TypeName CHARSEQUENCE_ARRAY = ArrayTypeName.of(CHARSEQUENCE);
    static final TypeName DOUBLE = ClassName.DOUBLE;
    static final TypeName DOUBLE_ARRAY = ArrayTypeName.of(DOUBLE);
    static final TypeName LIST = ClassName.get("java.util", "ArrayList");
    static final TypeName FLOAT = ClassName.FLOAT;
    static final TypeName FLOAT_ARRAY = ArrayTypeName.of(FLOAT);
    static final TypeName INT = ClassName.INT;
    static final TypeName INT_ARRAY = ArrayTypeName.of(int.class);
    static final TypeName LONG = ClassName.LONG;
    static final TypeName LONG_ARRAY = ArrayTypeName.of(LONG);
    static final TypeName PARCELABLE = ClassName.get("android.os", "Parcelable");
    static final TypeName PARCELABLE_ARRAY = ArrayTypeName.of(PARCELABLE);
    static final TypeName SERIALIZABLE = ClassName.get("java.io", "Serializable");
    static final TypeName SHORT = TypeName.SHORT;
    static final TypeName SHORT_ARRAY = ArrayTypeName.of(SHORT);
    static final TypeName SIZE = ClassName.get("android.util", "Size");
    static final TypeName SIZEF = ClassName.get("android.util", "SizeF");
    static final TypeName SPARSE_ARRAY = ClassName.get("android.util", "SparseArray");
    static final TypeName STRING = ClassName.get("java.lang", "String");
    static final TypeName STRING_ARRAY = ArrayTypeName.of(STRING);
    static final TypeName PERSISTABLE_BUNDLE = ClassName.get("android.os", "ParsistableBundle");
    static final TypeName MAP = ClassName.get("java.util", "HashMap");

    static final TypeName CHARSEQUENCE_ARRAYLIST = ParameterizedTypeName.get((ClassName) LIST,
            CHARSEQUENCE);
    static final TypeName INT_ARRAYLIST = ParameterizedTypeName.get((ClassName) LIST, INT.box());
    static final TypeName PARCELABLE_ARRAYLIST = ParameterizedTypeName.get((ClassName) LIST,
            PARCELABLE);
    static final TypeName SPARSE_PARCELABE_ARRAY = ParameterizedTypeName.get((ClassName) SPARSE_ARRAY,
            PARCELABLE);
    static final TypeName STRING_ARRAYLIST = ParameterizedTypeName.get((ClassName) LIST,
            STRING);

    private static Map<Element, TypeName> elementsMap = new LinkedHashMap<>();

    /**
     * Generates codeblock to persist elements from the oustate
     *
     * @param env
     * @param state
     * @param block
     * @param element
     * @throws ProcessingException
     */
    static void get(ProcessingEnvironment env, String state, CodeBlock.Builder block, Element element)
            throws ProcessingException {
        Types types = env.getTypeUtils();
        TypeName parcelableType;
        if (!elementsMap.keySet().contains(element)) {
            return;
        }
        parcelableType = getTypeName(types, element.asType());
        block.add(getCodeBlock(env, state, parcelableType, element));
    }

    private static CodeBlock getCodeBlock(ProcessingEnvironment env, String state,
                                          TypeName parcelableType,
                                          Element element) throws ProcessingException {
        CodeBlock.Builder builder = CodeBlock.builder();

        VariableElement variableElement = (VariableElement) element;
        String name = variableElement.getSimpleName().toString();

        if (parcelableType.equals(BUNDLE)) {
            builder.addStatement("target.$L = $L.getBundle($S)", name, state, name);
        } else if (parcelableType.equals(IBINDER)) {
            builder.addStatement("target.$L = $L.getBinder($S)", name, state, name);
        } else if (parcelableType.equals(BOOLEAN) || parcelableType.equals(BOOLEAN.box())) {
            builder.addStatement("target.$L = $L.getBoolean($S)", name, state, name);
        } else if (parcelableType.equals(BOOLEAN_ARRAY)) {
            builder.addStatement("target.$L = $L.getBooleanArray($S)", name, state, name);
        } else if (parcelableType.equals(BYTE) || parcelableType.equals(BYTE.box())) {
            builder.addStatement("target.$L = $L.getByte($S)", name, state, name);
        } else if (parcelableType.equals(BYTE_ARRAY)) {
            builder.addStatement("target.$L = $L.getByteArray($S)", name, state, name);
        } else if (parcelableType.equals(CHAR) || parcelableType.equals(CHAR.box())) {
            builder.addStatement("target.$L = $L.getChar($S)", name, state, name);
        } else if (parcelableType.equals(CHAR_ARRAY)) {
            builder.addStatement("target.$L = $L.getCharArray($S)", name, state, name);
        } else if (parcelableType.equals(CHARSEQUENCE)) {
            builder.addStatement("target.$L = $L.getCharSequence($S)", name, state, name);
        } else if (parcelableType.equals(CHARSEQUENCE_ARRAY)) {
            builder.addStatement("target.$L = $L.getCharSequenceArray($S)", name, state, name);
        } else if (parcelableType.equals(CHARSEQUENCE_ARRAYLIST)) {
            builder.addStatement("target.$L = $L.getCharSequenceArrayList($S)", name, state, name);
        } else if (parcelableType.equals(DOUBLE) || parcelableType.equals(DOUBLE.box())) {
            builder.addStatement("target.$L = $L.getDouble($S)", name, state, name);
        } else if (parcelableType.equals(DOUBLE_ARRAY)) {
            builder.addStatement("target.$L = $L.getDoubleArray($S)", name, state, name);
        } else if (parcelableType.equals(FLOAT) || parcelableType.equals(FLOAT.box())) {
            builder.addStatement("target.$L = $L.getFloat($S)", name, state, name);
        } else if (parcelableType.equals(FLOAT_ARRAY)) {
            builder.addStatement("target.$L = $L.getFloatArray($S)", name, state, name);
        } else if (parcelableType.equals(INT) || parcelableType.equals(INT.box())) {
            builder.addStatement("target.$L = $L.getInt($S)", name, state, name);
        } else if (parcelableType.equals(INT_ARRAY)) {
            builder.addStatement("target.$L = $L.getIntArray($S)", name, state, name);
        } else if (parcelableType.equals(INT_ARRAYLIST)) {
            builder.addStatement("target.$L = $L.getIntegerArrayList($S)", name, state, name);
        } else if (parcelableType.equals(LONG) || parcelableType.equals(LONG.box())) {
            builder.addStatement("target.$L = $L.getLong($S)", name, state, name);
        } else if (parcelableType.equals(LONG_ARRAY)) {
            builder.addStatement("target.$L = $L.getLongArray($S)", name, state, name);
        } else if (parcelableType.equals(PARCELABLE)) {
            builder.addStatement("target.$L = $L.getParcelable($S)", name, state, name);
        } else if (parcelableType.equals(PARCELABLE_ARRAY)) {
            builder.addStatement("target.$L = $L.getParcelableArray($S)", name, state, name);
        } else if (parcelableType.equals(PARCELABLE_ARRAYLIST)) {
            builder.addStatement("target.$L = $L.getParcelableArrayList($S)", name, state, name);
        } else if (parcelableType.equals(SHORT) || parcelableType.equals(SHORT.box())) {
            builder.addStatement("target.$L = $L.getShort($S)", name, state, name);
        } else if (parcelableType.equals(SHORT_ARRAY)) {
            builder.addStatement("target.$L = $L.getShortArray($S)", name, state, name);
        } else if (parcelableType.equals(SIZE)) {
            builder.addStatement("target.$L = $L.getSize($S)", name, state, name);
        } else if (parcelableType.equals(SIZEF)) {
            builder.addStatement("target.$L = $L.getSizeF($S)", name, state, name);
        } else if (parcelableType.equals(SPARSE_PARCELABE_ARRAY)) {
            builder.addStatement("target.$L = $L.getSparceParcelableArray($S)", name, state, name);
        } else if (parcelableType.equals(STRING)) {
            builder.addStatement("target.$L = $L.getString($S)", name, state, name);
        } else if (parcelableType.equals(STRING_ARRAY)) {
            builder.addStatement("target.$L = $L.getStringArray($S)", name, state, name);
        } else if (parcelableType.equals(STRING_ARRAYLIST)) {
            builder.addStatement("target.$L = $L.getStringArrayList($S)", name, state, name);
        } else if (parcelableType.equals(SERIALIZABLE)) {
            TypeElement typeElement = (TypeElement) env.getTypeUtils().asElement(element.asType());
            builder.addStatement("target.$L = ($T)$L.getSerializable($S)", name, ClassName.get(typeElement),
                    state, name);
        } else {
            throw new ProcessingException(element,
                    "The type @%s is not supported yet",
                    env.getTypeUtils().asElement(element.asType()).getSimpleName().toString());
        }

        return builder.build();
    }

    /**
     * Generates codeblock to put back elements into the bundle
     *
     * @param env
     * @param block
     * @param type
     * @param state
     * @param element
     * @throws ProcessingException
     */
    static void put(ProcessingEnvironment env, CodeBlock.Builder block, TypeName type,
                    String state, Element element) throws ProcessingException {
        String name = element.getSimpleName().toString();
        TypeName parcelableType = getTypeName(env.getTypeUtils(), element.asType());
        if (parcelableType.equals(BUNDLE)) {
            block.addStatement("$L.putBundle($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(IBINDER)) {
            block.addStatement("$L.putBinder($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(BOOLEAN) || parcelableType.equals(BOOLEAN.box())) {
            block.addStatement("$L.putBoolean($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(BOOLEAN_ARRAY)) {
            block.addStatement("$L.putBooleanArray($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(BYTE) || parcelableType.equals(BYTE.box())) {
            block.addStatement("$L.putByte($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(BYTE_ARRAY)) {
            block.addStatement("$L.putByteArray($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(CHAR) || parcelableType.equals(CHAR.box())) {
            block.addStatement("$L.putChar($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(CHAR_ARRAY)) {
            block.addStatement("$L.putCharArray($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(CHARSEQUENCE)) {
            block.addStatement("$L.putCharSequence($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(CHARSEQUENCE_ARRAY)) {
            block.addStatement("$L.putCharSequenceArray($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(CHARSEQUENCE_ARRAYLIST)) {
            block.addStatement("$L.putCharSequenceArrayList($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(DOUBLE) || parcelableType.equals(DOUBLE.box())) {
            block.addStatement("$L.putDouble($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(DOUBLE_ARRAY)) {
            block.addStatement("$L.putDoubleArray($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(FLOAT) || parcelableType.equals(FLOAT.box())) {
            block.addStatement("$L.putFloat($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(FLOAT_ARRAY)) {
            block.addStatement("$L.putFloatArray($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(INT) || parcelableType.equals(INT.box())) {
            block.addStatement("$L.putInt($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(INT_ARRAY)) {
            block.addStatement("$L.putIntArray($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(INT_ARRAYLIST)) {
            block.addStatement("$L.putIntegerArrayList($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(LONG) || parcelableType.equals(LONG.box())) {
            block.addStatement("$L.putLong($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(LONG_ARRAY)) {
            block.addStatement("$L.putLongArray($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(PARCELABLE)) {
            block.addStatement("$L.putParcelable($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(PARCELABLE_ARRAY)) {
            block.addStatement("$L.putParcelableArray($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(PARCELABLE_ARRAYLIST)) {
            block.addStatement("$L.putParcelableArrayList($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(SHORT) || parcelableType.equals(SHORT.box())) {
            block.addStatement("$L.putShort($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(SHORT_ARRAY)) {
            block.addStatement("$L.putShortArray($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(SIZE)) {
            block.addStatement("$L.putSize($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(SIZEF)) {
            block.addStatement("$L.putSizeF($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(SPARSE_PARCELABE_ARRAY)) {
            block.addStatement("$L.putSparseParcelableArray($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(STRING)) {
            block.addStatement("$L.putString($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(STRING_ARRAY)) {
            block.addStatement("$L.putStringArray($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(STRING_ARRAYLIST)) {
            block.addStatement("$L.putStringArrayList($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else if (parcelableType.equals(SERIALIZABLE)) {
            block.addStatement("$L.putSerializable($S,target.$L)", state, name, name);
            elementsMap.put(element, type);
        } else {
            throw new ProcessingException(element,
                    "The type @%s is not supported yet",
                    env.getTypeUtils().asElement(element.asType()).getSimpleName().toString());
        }
    }

    private static final Set<TypeName> VALID_TYPES = ImmutableSet.of(BUNDLE, IBINDER, BOOLEAN, BOOLEAN_ARRAY,
            BYTE, BYTE_ARRAY, CHAR, CHAR_ARRAY, CHARSEQUENCE, CHARSEQUENCE_ARRAY,
            CHARSEQUENCE_ARRAYLIST, DOUBLE, DOUBLE_ARRAY, FLOAT, FLOAT_ARRAY, INT, INT_ARRAY,
            INT_ARRAYLIST, LONG_ARRAY, PARCELABLE, PARCELABLE_ARRAY, PARCELABLE_ARRAYLIST,
            SERIALIZABLE, SHORT, SHORT_ARRAY, SIZE, SIZEF, SPARSE_PARCELABE_ARRAY, STRING,
            STRING_ARRAY, STRING_ARRAYLIST, PERSISTABLE_BUNDLE);

    private static TypeName getTypeName(Types types, TypeMirror typeMirror) {
        TypeElement element = (TypeElement) types.asElement(typeMirror);
        if (element != null) {
            return getParcelableType(types, element);
        }
        return ClassName.get(typeMirror);
    }

    private static TypeName getParcelableType(Types types, TypeElement type) {
        TypeMirror typeMirror = type.asType();
        while (typeMirror.getKind() != TypeKind.NONE) {
            TypeName typeName = TypeName.get(typeMirror);

            // first, check if the class is valid.
            if (typeName instanceof ParameterizedTypeName) {
                typeName = ((ParameterizedTypeName) typeName).rawType;
            }
            if (isValidType(typeName)) {
                return typeName;
            }

            // then check if it implements valid interfaces
            for (TypeMirror iface : type.getInterfaces()) {
                TypeName inherited = getParcelableType(types, (TypeElement) types.asElement(iface));
                if (inherited != null) {
                    return inherited;
                }
            }

            // then move on
            type = (TypeElement) types.asElement(typeMirror);
            typeMirror = type.getSuperclass();
        }
        return null;
    }

    private static boolean isValidType(TypeName typeName) {
        return typeName.isPrimitive() || typeName.isBoxedPrimitive() || VALID_TYPES.contains(typeName);
    }

    private static boolean isValidType(Types types, TypeMirror type) {
        // Special case for MAP, since it can only have String keys and Parcelable values
        if (isOfType(types, type, MAP)) {
            return isValidMap(types, type);
        }

        return getParcelableType(types, (TypeElement) types.asElement(type)) != null;
    }

    /**
     * Maps can only have String keys and Parcelable values.
     */
    private static boolean isValidMap(Types types, TypeMirror type) {
        return type.accept(new SimpleTypeVisitor6<Boolean, Types>() {
            @Override public Boolean visitDeclared(DeclaredType t, Types o) {
                List<? extends TypeMirror> args = t.getTypeArguments();
                if (args.size() == 2) {
                    TypeMirror key = args.get(0);
                    TypeMirror value = args.get(1);
                    if (STRING.equals(TypeName.get(key)) && isValidType(o, value)) {
                        return true;
                    }
                }
                return false;
            }
        }, types);
    }

    private static boolean isOfType(Types types, TypeMirror typeMirror, TypeName target) {
        TypeElement element = (TypeElement) types.asElement(typeMirror);
        while (typeMirror.getKind() != TypeKind.NONE) {
            TypeName typeName = TypeName.get(typeMirror);
            if (typeName instanceof ParameterizedTypeName) {
                typeName = ((ParameterizedTypeName) typeName).rawType;
            }
            if (typeName.equals(target)) {
                return true;
            }

            for (TypeMirror iface : element.getInterfaces()) {
                if (isOfType(types, iface, target)) {
                    return true;
                }
            }

            element = (TypeElement) types.asElement(typeMirror);
            typeMirror = element.getSuperclass();
        }
        return false;
    }
}
