package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by wangjf
 * Date 2019/4/16 19:07
 */
@Service
@Transactional
public class LabelService {
	@Autowired
	private LabelDao labelDao;
	@Autowired
	private IdWorker idWorker;

	public List<Label> findAll() {
		return labelDao.findAll();
	}

	public Label findById(String id) {
		return labelDao.findById(id).get();
	}

	public void save(Label label) {
		label.setId(idWorker.nextId() + "");
		labelDao.save(label);
	}

	public void update(Label label) {
		labelDao.save(label);
	}

	public void deleteById(String id) {
		labelDao.deleteById(id);
	}

	/**
	 * 构建查询条件
	 */
	private Specification<Label> createSpecification(Label label) {
		return new Specification<Label>() {
			public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				ArrayList<Predicate> list = new ArrayList<>();
				if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
					list.add(cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%"));
				}
				if (label.getState() != null && !"".equals(label.getState())) {
					list.add(cb.equal(root.get("state").as(String.class), label.getState()));
				}
				return cb.and(list.toArray(new Predicate[list.size()]));
			}
		};
	}

	public List<Label> findSearch(Label label) {
		Specification<Label> specification = createSpecification(label);
		return labelDao.findAll(specification);
	}


	public Page<Label> pageQuery(Label label, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page - 1, size);
		Specification<Label> specification = createSpecification(label);
		return labelDao.findAll(specification, pageRequest);
	}
}
