package com.atc.codes;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This class creates and keeps a singly linked list of 
 * class Node objects containing any type of data.
 * 
 * @author sepehr safari
 *
 * @param <T>
 */
@Component
public class QueueLinkedList<T> {
	private static final Logger LOG = LoggerFactory.getLogger(QueueLinkedList.class);
	
	private Node<T> head = null;
	private int count;
	private ListIterator<T> listIterator = new ListIterator<T>(this.head, null);
	private final ReentrantLock lock = new ReentrantLock();
	
	private static class Node<T> {
		private Comparable<T> data;
		private Node<T> next;
		
		public Node(Comparable<T> data, Node<T> next) {
			super();
			this.data = data;
			this.next = next;
		}
		public Comparable<T> getData() {
			return data;
		}
		public Node<T> getNext() {
			return next;
		}
	}
	
	public ReentrantLock getLock() {
		return lock;
	}

	public Comparable<T> getHeadData() {
		if (this.head != null)
			return head.data;
		return null;
	}

	public int getCount() {
		return count;
	}

	public void add(Comparable<T> newItem, Class<T> clazz) throws InterruptedException {
		lock.lock();
		try {
			if (count == 0) {
				head = new Node<T>(newItem, null);
				this.count += 1;
			} else {
				enqueue(newItem, clazz);
				this.count += 1;
			}
		} finally {
			lock.unlock();
		}
	}

	private void enqueue(Comparable<T> newItem, Class<T> clazz) {
		Node<T> cur = head;
		Node<T> prev = null;
		
		while (cur != null) {
			if (cur.data.compareTo(clazz.cast(newItem)) < 0)
				break;
			prev = cur;
			cur = cur.next;
		}
		if (prev != null) {
			prev.next = new Node<T>(newItem, cur);
		} else {
			Node<T> node = new Node<T>(newItem, cur);
			this.head = node;
		}
	}

	public Comparable<T> dequeue() throws InterruptedException {
		
		if (this.head == null)
			return null;
		
		lock.lock();
		try {
			Node<T> tmp = this.head;
			this.head = tmp.getNext();
			tmp.next = null;
			this.count--;
			return tmp.data;
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * Create an iterator for walking the queue.
	 * 
	 * @param clazz
	 * @return
	 */
	public Iterator<T> getIterator(Class<T> clazz) {
		this.listIterator.nextNode = this.head;
		this.listIterator.clazz = clazz;
		return listIterator;
	}
	
	/*public Iterator<T> iterator(Class<T> clazz) {
		return new ListIterator<T>(this.head, clazz);
	}*/
	
	private static class ListIterator<T>  implements Iterator<T> {
		private Node<T> nextNode;
		private Class<T> clazz;

		public ListIterator(Node<T> head, Class<T> clazz) {
			nextNode = head;
			this.clazz = clazz;
		}
		
		@Override
		public boolean hasNext() {
			return nextNode != null;
		}

		@Override
		public T next() {
			if (!hasNext()) 
				throw new NoSuchElementException();
			
			T res = this.clazz.cast(nextNode.getData());
			nextNode = nextNode.getNext();
			return res;
		}	
	}
}
