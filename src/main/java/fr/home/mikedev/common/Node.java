package fr.home.mikedev.common;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Node 
{
    private Pair<Integer> coord;
    @Builder.Default private List<Node> shortestPath = new LinkedList<>();
    @Builder.Default private Integer distance = Integer.MAX_VALUE;
    @Builder.Default Map<Node, Integer> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, int distance) 
    {
        adjacentNodes.put(destination, distance);
    }
}
