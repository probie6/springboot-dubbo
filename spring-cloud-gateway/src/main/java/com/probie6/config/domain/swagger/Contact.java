package com.probie6.config.domain.swagger;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 江山
 * @date: 2019/12/30 15:50
 * @description:
 */
@Data
@NoArgsConstructor
class Contact {
    private String name = "";
    private String url = "";
    private String email = "";
}
