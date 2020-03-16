package com.cpp.cs.cs4450.graphics;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

final class TexturedObjectConverter {


    private TexturedObjectConverter(){}


    static List<Textured> convert(final List<?> objects){
        final List<?> checked = filter(objects);
        final List<Textured> textured  = Collections.checkedList(new ArrayList<>(), Textured.class);
        for(Object obj : checked){
            if(obj instanceof Textured){
                textured.add((Textured) obj);
            } else if(obj instanceof Textured2D){
                textured.addAll(convert((Textured2D) obj));
            } else if(obj instanceof Textured3D){
                textured.addAll(convert((Textured3D) obj));
            }
        }
        return textured;
    }

    private static List<Textured> convert(final Textured2D matrix){
        return convert(matrix.getMatrix());
    }

    private static List<Textured> convert(final Textured[][] matrix){
        return Arrays.stream(matrix)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static List<Textured> convert(final Textured3D textured){
        return convert(textured.getTensor());
    }

    private static List<Textured> convert(final Textured[][][] tensor){
        return Arrays.stream(tensor)
                .map(TexturedObjectConverter::convert)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private static List<?> filter(final List<?> objects){
        return objects.stream()
                .filter(Objects::nonNull)
                .filter(TexturedObjectConverter::isTexturedType)
                .collect(Collectors.toList());
    }

    private static boolean isTexturedType(final Object obj){
        return obj instanceof Textured
                || obj instanceof Textured2D
                || obj instanceof Textured3D;
    }

}
