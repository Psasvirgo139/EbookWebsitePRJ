package service;

import filter.FilterResult;

public interface IContentFilterService {
    FilterResult check(String content);
    
}
