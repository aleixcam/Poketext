package domain.item;

public interface ItemRepository {

    ItemsCollection findByCriteria(ItemCriteria criteria);
}
