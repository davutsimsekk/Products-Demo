package kha.productsdemo.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortOptionService {
    public Sort createSort(String sort){
        String[] values = sort.split("-");
        String sortType = values[1];
        String sortField = values[0];
        System.out.println(sortType + " " + sortField);
        return (sortType.equals("asc")) ? Sort.by(Sort.Direction.ASC, sortField)
                : Sort.by(Sort.Direction.DESC, sortField);

    }
}
