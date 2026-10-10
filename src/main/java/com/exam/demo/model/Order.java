package com.exam.demo.model;

import java.util.List;

public record Order (
        String orderRef,
        List<Item> items,
        String channel
) {}