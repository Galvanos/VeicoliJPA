package com.betacom.veh.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Classe atta a contenere alcune funzioni di utilità, tendenzialmente statiche
 */
public class Utils {

	/**
	 * Data una list, rimuove i campi null, l'istanza restituita è una {@link LinkedList} 
	 * se l'istanza fornita in {@code list} era una {@link LinkedList}, altrimenti si restituisce una 
	 * {@link ArrayList} 
	 * @param <T> il tipo della lista da modificare
	 * @param list la lista originale
	 * @return una {@link ArrayList} o una {@link LinkedList}, in base a quella fornita a cui sono stati tolti i null
	 */
	public static <T> List<T> removeNulls(List<T> list){
		if(list instanceof LinkedList<T>) {
			return list.stream().filter(Objects::nonNull).collect(Collectors.toCollection(LinkedList<T>::new));
		}
		return list.stream().filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList<T>::new));
	}
	
	/**
	 * Data una list di stringhe, rimuove i campi null e blank ({@link String#isBlank()}), l'istanza restituita è una {@link LinkedList} 
	 * se l'istanza fornita in {@code list} era una {@link LinkedList}, altrimenti si restituisce una 
	 * {@link ArrayList} 
	 * @param list la lista originale
	 * @return una {@link ArrayList} o una {@link LinkedList} di {@link String}, in base a quella fornita a cui sono stati tolti i null
	 */
	public static List<String> removeNullsAndBlanks(List<String> list){
		if(list instanceof LinkedList<String>) {
			return list.stream().filter(Objects::nonNull).filter(t -> !t.isBlank()).collect(Collectors.toCollection(LinkedList<String>::new));
		}
		return list.stream().filter(Objects::nonNull).filter(t -> !t.isBlank()).collect(Collectors.toCollection(ArrayList<String>::new));
	}
	
	
}
