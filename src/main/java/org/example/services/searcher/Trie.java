package org.example.services.searcher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
public class Trie<T> implements PrefixStorage<T>{
    private final Node<T> root;

    public Trie() {
        root = new Node<>(new HashMap<>(), null);
    }

    @Override
    public void add(String line, T rowId) {
        var cur = root;
        var symbols = line.toCharArray();
        for (var symbol : symbols) {
            if (!cur.to.containsKey(symbol)) {
                cur.to.put(symbol, new Node<T>(new HashMap<>(), new ArrayList<>(List.of(rowId))));
            } else {
                cur.to.get(symbol).getRowIds().add(rowId);
            }
            cur = cur.to.get(symbol);
        }
    }

    @Override
    public List<T> findRowsByPrefix(String prefix) {
        var cur = root;
        var symbols = prefix.toCharArray();
        for (var symbol : symbols) {
            if (!cur.getTo().containsKey(symbol)) {
                return new ArrayList<>();
            } else {
                cur = cur.getTo().get(symbol);
            }
        }
        return cur.getRowIds();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Node<T> {
        Map<Character, Node<T>> to;
        List<T> rowIds;
    }
}