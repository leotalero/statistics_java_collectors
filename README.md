According to this statement
The test consists in reading a file and generate some statistics.
You should implement IReportAnalyze interface, so ReportAnalyzeTest tests can pass.
This is my solution
The test consists in reading a file and generate some statistics.
 


Implementing the class AnalisadorRelatorio that implements the interface IAnalisadorRaltorio and the class Event.

Class event has  this  variables whith the respectives getters and setters
private String sequential_code;
	private String customer_code;
	private String event_code;
	private String event_type;
	private Date start_date;
	private Date end_date;
	private String person_id;
	private Long attendance;
	private Tipo event_type_tipo;



the class AnalisadorRelatorio  has the implementation of the following methods
    Map<String, Integer> getTotalEventosCliente();

    Map<String, Long> getTempoMedioAtendimentoAtendente();

    
    List<Tipo> getTiposOrdenadosNumerosEventosDecrescente();

    List<Integer> getCodigoSequencialEventosDesarmeAposAlarme();



1.	First method    Map<String, Integer> getTotalEventosCliente();

Uses the Collectors Class available in  Java 8 
https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html
In order to group the collection by one attribute and count the number of register of each one

2.	Second method      Map<String, Long> getTempoMedioAtendimentoAtendente();

Uses the collections and the function Collectors.averagingLong the  Average attendance time by person.

3.	Third method	    List<Tipo> getTiposOrdenadosNumerosEventosDecrescente();
 Returns a list of event types, ordered by total events descending. Initially the  eventsOrdered  map is created with the Types and the numer of event of each one.
Then that map is ordered using LinkedList  and comparators

4.	fourth method  List<Integer> getCodigoSequencialEventosDesarmeAposAlarme();
Returns the sequential code of a disarm event that occurred after an alarm event.
Assuming that the events has to be of the same customer, grouping the events by customer and listing the events that are of our interest (type:ALARME and DESARME)  I used the function calculeEventsInTime that calculates the time difference between events,select the event that happens in the interval of 5 minutes after the ALARME  and return the sequential code
