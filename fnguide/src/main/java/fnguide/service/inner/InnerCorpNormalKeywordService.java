package fnguide.service.inner;

import fnguide.repository.Inner.InnerDiscoveredCorpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InnerCorpNormalKeywordService {

    private final InnerDiscoveredCorpRepository repository;

    
}
