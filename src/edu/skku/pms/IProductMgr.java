package edu.skku.pms;

import java.util.List;

public interface IProductMgr {

	void add(Product b) throws DuplicateException;

	void allList();

	List<Product> getList();

	Product search(int pcode) throws RecordNotFoundException;

	void update(Product b) throws RecordNotFoundException;

	boolean delete(int pcode) throws RecordNotFoundException;

	List<Product> search(String title);

	
}