package com.exam.model;

import java.util.List;

public record Order (
        String orderRef,
        List<Item> items,
        String channel
) {}