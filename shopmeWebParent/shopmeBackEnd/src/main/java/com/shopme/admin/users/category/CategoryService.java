package com.shopme.admin.users.category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.admin.users.CategoryRepository;
import com.shopme.admin.users.catController.CategoryperPage;
import com.shopme.common.entities.Category;

@Service()
public class CategoryService {

	public static final int ROOT_CATEGORY_PER_PAGE = 4; 
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category saveCategory(Category category)
	{
		Category saved = this.categoryRepository.save(category);
		return saved;
	}
	
	public List<Category> getAllCategories( CategoryperPage pageinfo , int pageNumber ,  String sortDir , String keyword)
	{
			
		Sort sort = Sort.by("name");
		Pageable page =  PageRequest.of(pageNumber - 1, ROOT_CATEGORY_PER_PAGE, sort);
		if(sortDir == null || sortDir.isEmpty()) {
			sort= sort.ascending();
		}
		else if(sortDir.equals("asc")) {
			sort=sort.ascending();
		}
		else if(sortDir.equals("desc")){
			sort=sort.descending();
		}
		
		Page<Category> pagecategory= null;
		if(keyword!=null && !keyword.isEmpty()) {
			pagecategory =  this.categoryRepository.search(keyword, page);
		}
		else {
			pagecategory = this.categoryRepository.listRootCategories(page);
		}
		
		List<Category> rootCategory = pagecategory.getContent();
		
		pageinfo.setTotalElements(pagecategory.getTotalElements());
		pageinfo.setTotalpage(pagecategory.getTotalPages());
		
		
		
		if(keyword!=null && !keyword.isEmpty())
		{
			List<Category> searchResult = pagecategory.getContent();
			
			/*
			 * for(Category category : searchResult) {
			 * category.setHasChildren(category.getChildren().size()>0); }
			 */
			return searchResult;
		}else {
			return listHirarchicalCategories(rootCategory , sortDir);
		}
		
	}
	
	public List<Category> getParentCategoyList()
	{
		return this.categoryRepository.listRootCategories(Sort.by("name").ascending());
	}
	

	List<Category> listHirarchicalCategories(List<Category> rootcategories , String sortDir)
	{
		
		List<Category> hirarchicalCategories = new ArrayList<>();
		
		
		for(Category rootCategory : rootcategories) {
			
			hirarchicalCategories.add(Category.copyFullName(rootCategory));
			
			Set<Category> children = sortedChildCategory(rootCategory.getChildren() , sortDir);
			
			for(Category subcategories : children)
			{
				String name  = "--"+subcategories.getName();
				hirarchicalCategories.add(Category.copyFull(subcategories, name));
				
				listsubHirarchicalCategories(hirarchicalCategories , subcategories , 1 , sortDir);
			}
			
		}
		return hirarchicalCategories;
	}
	
	public void listsubHirarchicalCategories(List<Category> hirarchicalCategories , Category parent , int level , String sortDir) {
		
		Set<Category> category = sortedChildCategory(parent.getChildren() , sortDir);
		
		int sublevel  =  level + 1;
		
		for(Category children  : category)
		{
			String name = "";
			for(int i=0;i<sublevel;i++)
			{
			name+="--";	
			}
			name+= children.getName();
			hirarchicalCategories.add(Category.copyFull(children, name));
			listsubHirarchicalCategories(hirarchicalCategories, children, sublevel , sortDir);
		}
		
		
		
		
		
		
		
		
	}
	public List<Category> listCategoriesUsedInForm()
	{
		List<Category> listCategories = new ArrayList<>();
		Iterable<Category> getAllCategory = this.categoryRepository.findAll();
		for(Category cate :  getAllCategory)
		{
			if(cate.getParent()==null)
			{
				
				listCategories.add(Category.copyIdAndName(cate));
				Set<Category> children  =  sortedChildCategory(cate.getChildren());
				
				for(Category child: children)
				{
					String name="--" + child.getName();
					listCategories.add(Category.copyIdAndName(child.getId(), name));
					printChildren(listCategories, child , 1);
				}
			}
		}
		
		
		
		return listCategories;
		
	}
	public void printChildren(List<Category> listcategories, Category parent , int subLevel)
	{
			
		int newsublevel =  subLevel + 1;
		Set<Category> children =  sortedChildCategory(parent.getChildren());
		
		for(Category child : children)
		{
			String name = "";
			for(int i=0 ; i < newsublevel;i++)
			{
				name+="--";
			}
			name+=child.getName();
			listcategories.add(Category.copyIdAndName(child.getId(), name));
			printChildren(listcategories, child, newsublevel);
			
		}
		
	}
	
	public Category UpdateCategoryById(Integer id) throws CategoryNotFountException
	{
			try {
				Category category = this.categoryRepository.findById(id).get();
				return category;
			} catch (Exception e) {
				throw new CategoryNotFountException("could not find any categhory with ID "+id);
			}
	}
	public Category getCategoryByID(Integer id) {
		return this.categoryRepository.findById(id).get();
	}
	
	public String checkUnique(Integer id , String name , String alise)
	{
		
		boolean isCreatingNew  = (id==null || id==0);
		Category categoryByName = this.categoryRepository.findByName(name);
		if(isCreatingNew) {
			
			if(categoryByName!=null) {
				return "DuplicateName";
			}
			else {
				Category category = this.categoryRepository.findByAlise(alise);
				if(category!=null) {
					return "DuplicateAlise";
				}
			}
		}
		else {
			
			if(categoryByName!=null  && categoryByName.getId()!=id) {
				return 	"DuplicateNameWithID";
			}
			Category categoryByAlise = this.categoryRepository.findByAlise(alise);
			if(categoryByAlise!=null  && categoryByAlise.getId()!=id) {
				return 	"DuplicateAliseWithID";
			}
			
			
			
		}
		
		
		return "OK";
	}
	private  SortedSet<Category>  sortedChildCategory(Set<Category> children){
			
		return sortedChildCategory(children, "asc");
	}
	
	private  SortedSet<Category>  sortedChildCategory(Set<Category> children , String sortDir)
	{
		SortedSet<Category> setofchildern  =new TreeSet<>(new Comparator<Category>() {

			
			@Override
			
			public int compare(Category child1, Category child2) {
				if(sortDir.equals("asc")) {
				return child1.getName().compareTo(child2.getName());
				}else {
				return child2.getName().compareTo(child1.getName());
				}
			}
		});
		
		 setofchildern.addAll(children);
		 return setofchildern;
	}
	
	public void DeleteCategoryById(Integer id) throws CategoryNotFountException
	{
		Long countById = this.categoryRepository.countById(id);
		if(countById==null || countById == 0) {
			throw new CategoryNotFountException("Cound not found any Category with ID "+id);
		}
		
		this.categoryRepository.deleteById(id);
	}
	
	
	
	
}
