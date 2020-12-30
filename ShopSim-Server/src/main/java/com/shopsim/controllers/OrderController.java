package com.shopsim.controllers;


import com.shopsim.config.ConfigUtil;
import com.shopsim.dao.OrderRepo;
import com.shopsim.forms.*;
import com.shopsim.models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class OrderController {
    private static final Logger ocLog = LogManager.getLogger(OrderController.class);

    @Autowired
    private OrderRepo orderRepo;

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/submit", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response> orderSubmissionHandler(@RequestBody OrderForm orderForm) {
        ocLog.info("Received Order Submission Post Request");
        Response resp = new Response();
        Order order = new Order();
        order.setUserId(orderForm.getUserId());
        order.setStoreId(orderForm.getStoreId());
        order.setOrderDate(orderForm.getOrderDate());
        order.setDeliveryDate(orderForm.getDeliveryDate());
        order.setPayMethod(orderForm.getPayMethod());
        try {
            orderRepo.submitOrder(order);
            ocLog.info("Saving Order to Database");
            order = orderRepo.getOrderById(order.getUserId());
            ocLog.info("Retrieving Saved Order");
        } catch (Exception e) {
            order = null;
            ocLog.warn("Order Submission Failed, Encountered: " + e);
        }
        if (order == null) {
            resp.setMessage("Order Submission Failed");
            resp.setType("error");
            ocLog.info("Returning Failed Order Submission Response");
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            int orderID = order.getId();
            for (ItemListForm item : orderForm.getItems()) {
                Item orderItem = new Item();
                ItemId itemId = new ItemId();
                itemId.setOrderId(orderID);
                itemId.setProductId(item.getProductId());
                orderItem.setItemId(itemId);
                orderItem.setQuantity(item.getQuantity());
                orderRepo.saveItem(orderItem);
                ocLog.info("Saving Order Item to Database");
            }
            ocLog.info("Retrieving Saved Items for Order");
            Order newOrder = orderRepo.getOrderById(order.getUserId());
            if (newOrder.getItems() == null) {
                resp.setMessage("Order Submission Failed");
                resp.setType("error");
                ocLog.info("Returning Failed Order Submission Response");
                return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                resp.setMessage("Order Submission Successful");
                resp.setType("data");
                resp.setBody(newOrder);
                ocLog.info("Order Submission Success, Returning Order Details in Response");
                return new ResponseEntity<>(resp, HttpStatus.CREATED);
            }
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/orders", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Response> getAllUserOrders(UserIDForm userID) {
        ocLog.info("Received Order Retrieval Post Request");
        Response resp = new Response();
        String userId = userID.userID;
        List<Order> userOrders;
        try {
            userOrders = orderRepo.getOrdersByUserId(userId);
        } catch (Exception e) {
            resp.setType("error");
            resp.setMessage("No Orders Exist For Entered UserId");
            ocLog.warn("Order Submission Failed, Encountered: " + e);
            ocLog.info("Returning Failed Order Retrieval Response");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
        resp.setType("data");
        resp.setMessage("Orders Found For This UserId");
        resp.setBody(userOrders);
        ocLog.info("Order Retrieval Success, Returning Orders for User: " + userId + " in Response");
        return new ResponseEntity<>(resp, HttpStatus.ACCEPTED);
    }
}

