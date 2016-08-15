package com.segunfamisa.icicle.processor;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
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

    static final TypeName CHARSEQUENCE_ARRAYLIST = ParameterizedTypeName.get((ClassName)LIST,
            CHARSEQUENCE);
    static final TypeName INT_ARRAYLIST = ParameterizedTypeName.get((ClassName)LIST, INT.box());
    static final TypeName PARCELABLE_ARRAYLIST = ParameterizedTypeName.get((ClassName)LIST,
            PARCELABLE);
    static final TypeName SPARSE_PARCELABE_ARRAY = ParameterizedTypeName.get((ClassName)SPARSE_ARRAY,
            PARCELABLE);
    static final TypeName STRING_ARRAYLIST = ParameterizedTypeName.get((ClassName)LIST,
            STRING);

    public static void put(CodeBlock.Builder block, TypeName type,
                           String state, VariableElement element) {
        String name = element.getSimpleName().toString();
        if (type.equals(BUNDLE)) {
            block.addStatement("$L.putBundle($S,target.$L)", state, name, name);
        } else if (type.equals(IBINDER)) {
            block.addStatement("$L.putBinder($S,target.$L)", state, name, name);
        } else if (type.equals(BOOLEAN) || type.equals(BOOLEAN.box())) {
            block.addStatement("$L.putBoolean($S,target.$L)", state, name, name);
        } else if (type.equals(BOOLEAN_ARRAY)) {
            block.addStatement("$L.putBooleanArray($S,target.$L)", state, name, name);
        } else if (type.equals(BYTE) || type.equals(BYTE.box())) {
            block.addStatement("$L.putByte($S,target.$L)", state, name, name);
        } else if (type.equals(BYTE_ARRAY)) {
            block.addStatement("$L.putByteArray($S,target.$L)", state, name, name);
        } else if (type.equals(CHAR) || type.equals(CHAR.box())) {
            block.addStatement("$L.putChar($S,target.$L)", state, name, name);
        } else if (type.equals(CHAR_ARRAY)) {
            block.addStatement("$L.putCharArray($S,target.$L)", state, name, name);
        } else if (type.equals(CHARSEQUENCE)) {
            block.addStatement("$L.putCharSequence($S,target.$L)", state, name, name);
        } else if (type.equals(CHARSEQUENCE_ARRAY)) {
            block.addStatement("$L.putCharSequenceArray($S,target.$L)", state, name, name);
        } else if (type.equals(CHARSEQUENCE_ARRAYLIST)) {
            block.addStatement("$L.putCharSequenceArrayList($S,target.$L)", state, name, name);
        } else if (type.equals(DOUBLE) || type.equals(DOUBLE.box())) {
            block.addStatement("$L.putDouble($S,target.$L)", state, name, name);
        } else if (type.equals(DOUBLE_ARRAY)) {
            block.addStatement("$L.putDoubleArray($S,target.$L)", state, name, name);
        } else if (type.equals(FLOAT) || type.equals(FLOAT.box())) {
            block.addStatement("$L.putFloat($S,target.$L)", state, name, name);
        } else if (type.equals(FLOAT_ARRAY)) {
            block.addStatement("$L.putFloatArray($S,target.$L)", state, name, name);
        } else if (type.equals(INT) || type.equals(INT.box())) {
            block.addStatement("$L.putInt($S,target.$L)", state, name, name);
        } else if (type.equals(INT_ARRAY)) {
            block.addStatement("$L.putIntArray($S,target.$L)", state, name, name);
        } else if (type.equals(INT_ARRAYLIST)) {
            block.addStatement("$L.putIntegerArrayList($S,target.$L)", state, name, name);
        } else if (type.equals(LONG) || type.equals(LONG.box())) {
            block.addStatement("$L.putLong($S,target.$L)", state, name, name);
        } else if (type.equals(LONG_ARRAY)) {
            block.addStatement("$L.putLongArray($S,target.$L)", state, name, name);
        } else if (type.equals(PARCELABLE)) {
            block.addStatement("$L.putParcelable($S,target.$L)", state, name, name);
        } else if (type.equals(PARCELABLE_ARRAY)) {
            block.addStatement("$L.putParcelableArray($S,target.$L)", state, name, name);
        } else if (type.equals(PARCELABLE_ARRAYLIST)) {
            block.addStatement("$L.putParcelableArrayList($S,target.$L)", state, name, name);
        } else if (type.equals(SHORT) || type.equals(SHORT.box())) {
            block.addStatement("$L.putShort($S,target.$L)", state, name, name);
        } else if (type.equals(SHORT_ARRAY)) {
            block.addStatement("$L.putShortArray($S,target.$L)", state, name, name);
        } else if (type.equals(SIZE)) {
            block.addStatement("$L.putSize($S,target.$L)", state, name, name);
        } else if (type.equals(SIZEF)) {
            block.addStatement("$L.putSizeF($S,target.$L)", state, name, name);
        } else if (type.equals(SPARSE_PARCELABE_ARRAY)) {
            block.addStatement("$L.putSparseParcelableArray($S,target.$L)", state, name, name);
        } else if (type.equals(STRING)) {
            block.addStatement("$L.putString($S,target.$L)", state, name, name);
        } else if (type.equals(STRING_ARRAY)) {
            block.addStatement("$L.putStringArray($S,target.$L)", state, name, name);
        } else if (type.equals(STRING_ARRAYLIST)) {
            block.addStatement("$L.putStringArrayList($S,target.$L)", state, name, name);
        }
    }

    private static Set<TypeName> VALID_TYPES = new HashSet<>();
    static {
        Set<TypeName> validTypes = new HashSet<>();
        validTypes.add(BUNDLE);
        validTypes.add(IBINDER);
        validTypes.add(BOOLEAN);
        validTypes.add(BOOLEAN_ARRAY);
        validTypes.add(BYTE);
        validTypes.add(BYTE_ARRAY);
        validTypes.add(CHAR);
        validTypes.add(CHAR_ARRAY);
        validTypes.add(CHARSEQUENCE);
        validTypes.add(CHARSEQUENCE_ARRAY);
        validTypes.add(DOUBLE);
        validTypes.add(DOUBLE_ARRAY);
        validTypes.add(LIST);
        validTypes.add(FLOAT);
        validTypes.add(FLOAT_ARRAY);
        validTypes.add(INT);
        validTypes.add(INT_ARRAY);
        validTypes.add(INT_ARRAYLIST);
        validTypes.add(LONG);
        validTypes.add(LONG_ARRAY);
        validTypes.add(PARCELABLE);
        validTypes.add(PARCELABLE_ARRAY);
        validTypes.add(PARCELABLE_ARRAYLIST);
        validTypes.add(SERIALIZABLE);
        validTypes.add(SHORT);
        validTypes.add(SHORT_ARRAY);
        validTypes.add(SIZE);
        validTypes.add(SIZEF);
        validTypes.add(SPARSE_PARCELABE_ARRAY);
        validTypes.add(STRING);
        validTypes.add(STRING_ARRAY);
        validTypes.add(STRING_ARRAYLIST);
        validTypes.add(PERSISTABLE_BUNDLE);

        VALID_TYPES = validTypes;

    }
}
