package com.probie6.config.domain.swagger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GlobalResponseMessageBody {
        private int code;
        private String message;
        private String modelRef;
}