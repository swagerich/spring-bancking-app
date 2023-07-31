package com.erich.dev.dto.proyection;

import com.erich.dev.dto.TransactionDto;


import java.util.List;
import java.util.Map;

public record TransactionPageByUser(List<TransactionDto> transactions, Map<String,Object> pages) {
}
