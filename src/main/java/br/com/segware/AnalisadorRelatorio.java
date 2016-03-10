package br.com.segware;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class AnalisadorRelatorio implements IAnalisadorRelatorio {

	private List<Event> events;
	
	
	public AnalisadorRelatorio() {
		if(events==null){
			events=new ArrayList<Event>();
			events=readingFile();
		}
		
	}

	@Override
	public Map<String, Integer> getTotalEventosCliente() {
		
		Map<String, Integer> myMap = new HashMap<String,Integer>();
		if(events.size()>0){
			         
		   Map<String, Long> totalByCustomer_code = events.stream().collect(Collectors.groupingBy(
				   Event::getCustomer_code,Collectors.counting()));
		   
		   ///if the interface returns  map Map<String, Integer> then
		  
		   for (Entry<String, Long> entry : totalByCustomer_code.entrySet()) {
			   myMap.put(entry.getKey(), entry.getValue().intValue());
			}
		   
		            
		}else{
			System.err.println("Error no events in file");
			
		}
		
	
		return myMap;
	}

	@Override
	public Map<String, Long> getTempoMedioAtendimentoAtendente() {
		
		 Map<String, Long> myMap = new HashMap<String,Long>();
		 Map<String, Double> averageTimeAttendance = events.stream().collect(Collectors.groupingBy(
				   Event::getPerson_id,Collectors.averagingLong(Event::getAttendance)));
		
		 
		 ///if the interface returns  map Map<String, Long> then
		     for (Entry<String, Double> entry : averageTimeAttendance.entrySet()) {
				
		    	 myMap.put(entry.getKey(), Math.round(entry.getValue()));
			}
		   
		 
		return myMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
		// TODO Auto-generated method stub
		List<Tipo> list=new ArrayList<Tipo>();
		
		
	
		 Map<Tipo, Long> eventsOrdered = events.stream().collect(Collectors.groupingBy(
				   Event::getEvent_type_tipo,Collectors.counting()));
			 
		 List<Tipo> lista = new LinkedList(eventsOrdered.entrySet());
	       
	       Collections.sort(lista, new Comparator<Object>() {
	            public int compare(Object o2, Object o1) {
	               return ((Comparable) ((Map.Entry) (o1)).getValue())
	                  .compareTo(((Map.Entry) (o2)).getValue());
	            }
	       });
	       for (Iterator it = lista.iterator(); it.hasNext();) {
	              Map.Entry entry = (Map.Entry) it.next();
	              Tipo tip=(Tipo)entry.getKey();
	             list.add(tip);
	             
	       } 
		
		return list;
	}

	@Override
	public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
		List<Integer> result= new ArrayList<Integer>();
		//grouping by customer 
		Map<String, List<Event>> byCustomer
        = events.stream()
                   .collect(Collectors.groupingBy(Event::getCustomer_code));
		
		Long timediference=300000L;
		Map<String, List<Event>> customerswithAlarDis = new HashMap<String, List<Event>>();
		
		
		for (Entry<String, List<Event>> entry : byCustomer.entrySet()) {
			  List<Event> listado = entry.getValue();
			  List<Event> temporalList=new ArrayList<Event>();
			  boolean flagalarma=false;
			  boolean flagdesarme=false;
			  /* getting for each customer code events type ALARME and DESARME */
			  for(Event event:listado){
			  
				  if(event.getEvent_type_tipo().equals(Tipo.ALARME)){
					flagalarma=true;
					temporalList.add(event); 
				  }
				  
				  if(event.getEvent_type_tipo().equals(Tipo.DESARME)){
					 flagdesarme=true;
					 temporalList.add(event);
				  }
				  
				  
			  }
			  /* saving events ALARME and  DESARME in map*/
			  if(flagalarma && flagdesarme){
				  customerswithAlarDis.put(entry.getKey(),temporalList);
					 
			  }
			  
			  
			}
		/*calling method that return  the list of sequencial code based in the time diference*/
		 result= calculeEventsInTime(customerswithAlarDis,timediference);
		 Collections.sort(result);
		
		
		
		return result;
	}

	
	
	
	
	
	
	
	
	
	//---------------------------- methods
	
	
	private List<Integer> calculeEventsInTime(
			Map<String, List<Event>> customerswithAlarDis, Long timediference) {
		
		
		List<Integer> result=new ArrayList<Integer>();
		
		 for (Entry<String, List<Event>> entry : customerswithAlarDis.entrySet()) {
			
			
			  List<Event> listado = entry.getValue();
			  
			 		  for (int i = 0; i < listado.size(); i++) {
						    for (int k = i + 1; k < listado.size(); k++) {
						    	Event firstelement = listado.get(i);
						    	Event secondelement = listado.get(k);
						    	Long difference = secondelement.getStart_date().getTime() - firstelement.getStart_date().getTime();
						    	
						        if (difference>0 && firstelement.getEvent_type_tipo().equals(Tipo.ALARME) && difference<=timediference) {
						            result.add(Integer.valueOf(secondelement.getSequential_code()));
						        }
						    }
						} 
				  }
				 
				  
				  
			 
				
		
		
		return result;
	}

	
	
	public List<Event> readingFile(){
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader("src/test/java/br/com/segware/relatorio.csv"));
			//br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				Event event=new Event();
			        // use comma as separator
				String[] line_read = line.split(cvsSplitBy);
				event.setSequential_code(line_read[0]);
				event.setCustomer_code(line_read[1]);
				event.setEvent_code(line_read[2]);
				
				switch (line_read[3]) {
				case "ALARME":
					event.setEvent_type_tipo(Tipo.ALARME);
					break;
		 
				case "ARME":
					event.setEvent_type_tipo(Tipo.ARME);
					break;
		 
				case "DESARME":
					event.setEvent_type_tipo(Tipo.DESARME);
					break;
				case "TESTE":
					event.setEvent_type_tipo(Tipo.TESTE);
					break;
		 		default:
					System.out.println("no recognize tipo");
					break;
				}
				
				event.setEvent_type(line_read[3]);			
				event.setStart_date(formatter.parse(line_read[4]));
				event.setEnd_date(formatter.parse(line_read[5]));
				event.setPerson_id(line_read[6]);
				event.setAttendance((formatter.parse(line_read[5]).getTime()-formatter.parse(line_read[4]).getTime())/1000);
				events.add(event);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done reading file");
		return events;
	  }

		
	
	
	
	
}
