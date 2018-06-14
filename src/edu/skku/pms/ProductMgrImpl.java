package edu.skku.pms;

import java.util.ArrayList;
import java.util.List;



public class ProductMgrImpl implements IProductMgr {	
	
	private List<Product> ps = new ArrayList<Product>();
		
	public void add(Product b) throws DuplicateException{
		//구현하세요
		try {
			search(b.getPcode());
			throw new DuplicateException();
		} catch (RecordNotFoundException e) {
			ps.add(b);
		}
	}

	public void allList(){
//		Collections.sort(emps, new EmployeeComparator());
		for(int i=0; i<ps.size(); i++){
			System.out.println(ps.get(i));
		}	
	}

	public List<Product>  getList(){
    	return ps;
    }

	
	public Product search(int pcode) throws RecordNotFoundException{
		//구현하세요
		for(Product p :ps) {
			if(p.getPcode()==pcode) {
				return p;
			}
		}
		throw new RecordNotFoundException();
	}

	public void update(Product b) throws RecordNotFoundException {
		//구현하세요
		Product p=search(b.getPcode());
		p.setColor(b.getColor());
		p.setPrice(p.getPrice());
		p.setTitle(b.getTitle());
	}

	public boolean delete(int pcode) throws RecordNotFoundException {
		//구현하세요
		Product p=search(pcode);
		ps.remove(p);
		
		
		return true;
		
	}

	public List<Product> search(String title) {
		//구현하세요
		List<Product> pl = new ArrayList<Product>();
		for(Product p:ps) {
			if(title.equals(p.getTitle())) {
				pl.add(p);
			}
		}return pl;
	}

}
