package com.exam.demo.service;

import com.exam.demo.model.Item;
import com.exam.demo.model.Order;
import com.exam.demo.model.User;
import com.exam.demo.model.http.ItemDTO;
import com.exam.demo.model.http.PedidoDTO;
import com.exam.demo.model.http.UserRequest;
import com.exam.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    private final String PEDIDOS_URL = "https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/pedidos";
    private final String ITEMS_URL = "https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/items";

    public User getUserById(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void createUser(UserRequest request) {
        User newUser = new User(
                request.getUserId(),
                request.getName(),
                request.getFirstSurname(),
                request.getSecondSurname(),
                request.getAddress(),
                fetchUserOrders(request.getUserId())
        );
        userRepository.save(newUser);
    }

    private List<Order> fetchUserOrders(String userId) {
        List<Order> orders= new ArrayList<>();
        PedidoDTO[] rawPedidos = restTemplate.getForObject(PEDIDOS_URL, PedidoDTO[].class);
        ItemDTO[] rawItems = restTemplate.getForObject(ITEMS_URL, ItemDTO[].class);
        List<PedidoDTO> pedidos = rawPedidos != null ? Arrays.asList(rawPedidos) : Collections.emptyList();
        List<ItemDTO> items = rawItems != null ? Arrays.asList(rawItems) : Collections.emptyList();
        for (PedidoDTO pedido : pedidos) {
            if (pedido.userId().equals(userId)) {
                Order order = new Order();
                order.setOrderRef(pedido.orderRef());
                order.setChannel(pedido.canal());
                order.setItems(fetchItems(pedido.items(), items));
                orders.add(order);
            }
        }
        return orders;
    }

    private List<Item> fetchItems(List<String> itemsPedido, List<ItemDTO> items) {
        List<Item> orderItems = new ArrayList<>();
        for (String itemPedido : itemsPedido) {
            for (ItemDTO item : items) {
                if (itemPedido.equals(item.itemId())) {
                    Item orderItem = new Item();
                    orderItem.setItemId(item.itemId());
                    orderItem.setQuantity(item.quantity());
                    orderItem.setDeliveryStatus(item.deliveryStatus());
                    orderItems.add(orderItem);
                }
            }
        }
        return orderItems;
    }


}