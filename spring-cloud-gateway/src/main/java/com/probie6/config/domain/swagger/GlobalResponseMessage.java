package com.probie6.config.domain.swagger;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
class GlobalResponseMessage {
    List<GlobalResponseMessageBody> post = new ArrayList<>();
    List<GlobalResponseMessageBody> get = new ArrayList<>();
    List<GlobalResponseMessageBody> put = new ArrayList<>();
    List<GlobalResponseMessageBody> patch = new ArrayList<>();
    List<GlobalResponseMessageBody> delete = new ArrayList<>();
    List<GlobalResponseMessageBody> head = new ArrayList<>();
    List<GlobalResponseMessageBody> options = new ArrayList<>();
    List<GlobalResponseMessageBody> trace = new ArrayList<>();
}