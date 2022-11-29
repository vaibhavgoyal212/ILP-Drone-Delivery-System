package uk.ac.ed.inf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.List;

public class writeToJSON {

    public writeToJSON(){}

    public static void deliveriesFiles(Order[] orders, String date){
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        for(Order order : orders){
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("orderNo", order.orderNo);
            objectNode.put("outcome", order.getOutcome().toString());
            objectNode.put("costInPence", order.priceTotalInPence);
            arrayNode.add(objectNode);

        }
        try{
            mapper.writerWithDefaultPrettyPrinter().writeValue(new java.io.File("./deliveries-"+date+".json"), arrayNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void flightPathFiles(List<Node> flightPath, String date){
        ObjectMapper mapper =  new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        for(Node node : flightPath){
            if(node.isStartNode()){
                continue;
            }
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("orderNo", node.getOrderNo());
            objectNode.put("fromLongitude", node.getParent().getPosition().lng());
            objectNode.put("fromLatitude", node.getParent().getPosition().lat());
            objectNode.put("angle", node.getAngle());
            objectNode.put("toLongitude", node.getPosition().lng());
            objectNode.put("toLatitude", node.getPosition().lat());
            objectNode.put("ticksSinceStartOfCalculation", 0);
            arrayNode.add(objectNode);
        }
        try{
            mapper.writerWithDefaultPrettyPrinter().writeValue(new java.io.File("./flightpath-"+date+".json"), arrayNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void droneFile()






}
