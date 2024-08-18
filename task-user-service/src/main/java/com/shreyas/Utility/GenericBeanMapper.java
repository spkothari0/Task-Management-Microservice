package com.shreyas.Utility;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class GenericBeanMapper {
    public static <S, T> T map(S source, Class<T> targetClass, ModelMapper modelMapper) {
        return modelMapper.map(source, targetClass);
    }

    public static <S, T> List<T> mapList(List<S> sourceList, Class<T> targetClass, ModelMapper modelMapper) {
        return sourceList.stream()
                .map(source -> map(source, targetClass, modelMapper))
                .collect(Collectors.toList());
    }
}
