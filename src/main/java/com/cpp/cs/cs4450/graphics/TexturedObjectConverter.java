/***************************************************************
 * file: TexturedObjectConverted.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose: Utility class used to filter and convert Textured Objects
 *
 ****************************************************************/
package com.cpp.cs.cs4450.graphics;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

final class TexturedObjectConverter {


    private TexturedObjectConverter(){}

    /*
    Conertes a list of objects to textured objects
     */
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

    /*
    Conertes Textured2D object to textured objects
     */
    private static List<Textured> convert(final Textured2D matrix){
        return convert(matrix.getMatrix());
    }

    /*
    Conertes a 2D array of objects to textured objects
     */
    private static List<Textured> convert(final Textured[][] matrix){
        return Arrays.stream(matrix)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /*
    Conertes Textured3D object to textured objects
     */
    private static List<Textured> convert(final Textured3D textured){
        return convert(textured.getTensor());
    }

    /*
    Conertes a 3D array of objects to textured objects
     */
    private static List<Textured> convert(final Textured[][][] tensor){
        return Arrays.stream(tensor)
                .map(TexturedObjectConverter::convert)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /*
    Filters list of objects to only textured objects
     */
    private static List<?> filter(final List<?> objects){
        return objects.stream()
                .filter(Objects::nonNull)
                .filter(TexturedObjectConverter::isTexturedType)
                .collect(Collectors.toList());
    }

    /*
    Checks if objects are textured
     */
    private static boolean isTexturedType(final Object obj){
        return obj instanceof Textured
                || obj instanceof Textured2D
                || obj instanceof Textured3D;
    }

}
