package com.ns.common.util.tree;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TreeNode<K, V> {
    private K key;
    private V value;
    private Set<TreeNode<K, V>> childs = new HashSet<TreeNode<K, V>>();

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Set<TreeNode<K, V>> getChilds() {
        return childs;
    }

    public TreeNode() {
        super();
    }

    public TreeNode(K key) {
        super();
        this.key = key;
    }

    public TreeNode(K key, V value) {
        super();
        this.key = key;
        this.value = value;
    }

    public TreeNode<K, V> findChild(K key) {
        Iterator<TreeNode<K, V>> iterator = this.childs.iterator();
        while (iterator.hasNext()) {
            TreeNode<K, V> next = iterator.next();
            if (next.getKey().equals(key)) {
                return next;
            }
        }
        return null;
    }

    public TreeNode<K, V> addChild(TreeNode<K, V> child) {
        this.childs.add(child);
        return this;
    }

    @Override
    public String toString() {
        return "Node [key=" + key + ", value=" + value + ", childs=" + childs + "]";
    }
}
