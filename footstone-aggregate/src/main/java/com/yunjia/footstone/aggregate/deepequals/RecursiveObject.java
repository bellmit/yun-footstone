/*
 * Copyright (c) 2021. sunkaiyun1206@163.com.
 */
package com.yunjia.footstone.aggregate.deepequals;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * <p>
 * The recursive object used by DeepEquals
 * </p>
 *
 * @author sunkaiyun
 * @date 2021/12/13 3:29 PM
 */
public class RecursiveObject {

    private Set<DualObject> visited = new HashSet<>();
    private Stack<DualObject> stack = new Stack<>();

    private void addVisited(DualObject object) {
        visited.add(object);
    }

    public void push(DualObject dk) {
        if (!visited.contains(dk)) {
            stack.push(dk);
        }
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public DualObject pop() {
        DualObject object = stack.pop();
        addVisited(object);

        return object;
    }
}
