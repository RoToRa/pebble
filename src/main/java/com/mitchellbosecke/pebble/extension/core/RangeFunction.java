/*******************************************************************************
 * This file is part of Pebble.
 *
 * Copyright (c) 2014 by Mitchell Bösecke
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.mitchellbosecke.pebble.extension.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mitchellbosecke.pebble.extension.Function;

/**
 * Range function to iterate over long or a string with a length of 1.
 *
 * @author Eric Bussieres
 */
public class RangeFunction implements Function {
    public static final String FUNCTION_NAME = "range";
    private static final String PARAM_END = "end";
    private static final String PARAM_INCREMENT = "increment";
    private static final String PARAM_START = "start";
    private final List<String> argumentNames = new ArrayList<>();

    public RangeFunction() {
        this.argumentNames.add(PARAM_START);
        this.argumentNames.add(PARAM_END);
        this.argumentNames.add(PARAM_INCREMENT);
    }

    @Override
    public Object execute(Map<String, Object> args) {
        Object start = args.get(PARAM_START);
        Object end = args.get(PARAM_END);
        Long increment = (Long) args.get(PARAM_INCREMENT);
        if (increment == null) {
            increment = 1L;
        }

        List<Object> results = new ArrayList<>();
        // Iterating over Long
        if (start instanceof Long && end instanceof Long) {
            Long startNum = (Long) start;
            Long endNum = (Long) end;

            if (increment > 0) {
                for (Long i = startNum; i <= endNum; i += increment) {
                    results.add(i);
                }
            }
            else if (increment < 0) {
                for (Long i = startNum; i >= endNum; i += increment) {
                    results.add(i);
                }
            }
            else {
                throw new IllegalArgumentException("The increment of the range function must be different than 0");
            }
        }
        // Iterating over character
        else if (start instanceof String && end instanceof String) {
            String startStr = (String) start;
            String endStr = (String) end;
            if (startStr.length() != 1 || endStr.length() != 1) {
                throw new IllegalArgumentException("Arguments of range function must be of type Number or String with "
                        + "a length of 1");
            }

            char startChar = startStr.charAt(0);
            char endChar = endStr.charAt(0);

            if (increment > 0) {
                for (int i = startChar; i <= endChar; i += increment) {
                    results.add((char) i);
                }
            }
            else if (increment < 0) {
                for (int i = startChar; i >= endChar; i += increment) {
                    results.add((char) i);
                }
            }
            else {
                throw new IllegalArgumentException("The increment of the range function must be different than 0");
            }
        }
        else {
            throw new IllegalArgumentException("Arguments of range function must be of type Number or String with a "
                    + "length of 1");
        }

        return results;
    }

    @Override
    public List<String> getArgumentNames() {
        return this.argumentNames;
    }
}
