package com.atc.codes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/atc")
public class AirTrafficController {
	private static final Logger LOG = LoggerFactory.getLogger(AirTrafficController.class);

	@Autowired
	QueueLinkedList<AirCraft> atcq;
	
	@Autowired
	ATCRepository database;
	
	@RequestMapping("/list")
	public String listAllAircrafts(Model model) {
		List<AirCraft> aircrafts = null;
		Iterator<AirCraft> it = atcq.getIterator(AirCraft.class);
		atcq.getLock().lock();
		
		try {
			if (it.hasNext()) {
				aircrafts = new ArrayList<AirCraft>();
				while(it.hasNext()) {
					aircrafts.add(it.next());
				}
			}
		} finally {
			atcq.getLock().unlock();
		}
		model.addAttribute("list", aircrafts);
		return "aircraft/list";
	}
	
	/**
	 * Create an aircraft object from html form page.
	 * 
	 * @param model
	 */
	@RequestMapping("/aircraft/post")
	public String post(Model model) {
		model.addAttribute("plane", new AirCraft());
		return "aircraft/postForm";
	}
	
	/**
	 * Add new aircraft to the ATC queue.
	 * 
	 * @param item
	 * @return
	 */
	@RequestMapping(value = "/aircraft/add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute(value="plane") AirCraft item, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "aircraft/postForm";
		} else {
			try {
				atcq.add(item, AirCraft.class);
				database.save(item);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "redirect:/";
		}
	}
	
	@RequestMapping("/aircraft/remove")
	public String remove(Model model) {
		try {
			atcq.dequeue();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
}
