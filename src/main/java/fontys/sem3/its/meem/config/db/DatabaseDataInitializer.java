package fontys.sem3.its.meem.config.db;

import fontys.sem3.its.meem.persistence.repository.CategoryRepository;
import fontys.sem3.its.meem.persistence.repository.SortingGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseDataInitializer {

    private CategoryRepository categoryRepository;
    private SortingGroupRepository sortingGroupRepository;

//    @EventListener(ApplicationReadyEvent.class)
//    public void populateDatabaseInitialDummyData() {
//        if (categoryRepository.count() == 0) {
//            categoryRepository.save(CategoryEntity.builder().categoryName("Rage comics").build());
//            categoryRepository.save(CategoryEntity.builder().categoryName("Cars").build());
//            categoryRepository.save(CategoryEntity.builder().categoryName("Girls").build());
//            categoryRepository.save(CategoryEntity.builder().categoryName("Sports").build());
//            categoryRepository.save(CategoryEntity.builder().categoryName("MAGA").build());
//            categoryRepository.save(CategoryEntity.builder().categoryName("Shit-posting").build());
//            categoryRepository.save(CategoryEntity.builder().categoryName("Schizo-posting").build());
//            categoryRepository.save(CategoryEntity.builder().categoryName("Gamers").build());
//            categoryRepository.save(CategoryEntity.builder().categoryName("Balkan-posting").build());
//
//        }
//
//        if (sortingGroupRepository.count() == 0) {
//            sortingGroupRepository.save(SortingGroupEntity.builder().groupName("All-time").build());
//            sortingGroupRepository.save(SortingGroupEntity.builder().groupName("Hot").build());
//            sortingGroupRepository.save(SortingGroupEntity.builder().groupName("Rising").build());
//            sortingGroupRepository.save(SortingGroupEntity.builder().groupName("Fresh").build());
//        }
//    }
}
