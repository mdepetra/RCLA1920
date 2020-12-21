# RCLA1920

Assegnamenti per il Modulo di Laboratorio del corso di Reti di Calcolatori - A (2019/2020) @ Università di Pisa.

### Assegnamento 1: PiGreco
Il programma riceve in input da linea di comando un parametro che indica il grado di accuratezza (accuracy) per il calcolo di π ed il tempo massimo di attesa dopo cui il programma principale interomp
thread T. 
Attiva un thread T che effettua un ciclo infinito per il calcolo approssimato di π usando la serie di Gregory-Leibniz ( π = 4/1 – 4/3 + 4/5 - 4/7 + 4/9 - 4/11 ...).
Il thread esce dal ciclo quando una delle due condizioni seguenti risulta verificata:
1) il thread è stato interrotto
2) la differenza tra il valore stimato di π ed il valore Math.PI (della libreria JAVA) è minore di accuracy

### Assegnamento 2: UfficioPostale
Il programma simula il flusso di clienti in un ufficio postale che ha 4 sportelli.

L'ufficio ha 2 sale: un'ampia sala d'attesa in cui ogni persona può entrare liberamente. Quando entra, ogni persona prende il numero dalla numeratrice e aspetta il proprio turno in questa sala; una seconda sala, meno ampia, posta davanti agli sportelli, in cui si può entrare solo a gruppi di k persone. 

Comportamento di un cliente:
- Arrivo all'ufficio postale;
- Si mette prima in coda nella prima sala;
- Poi passa nella seconda sala;
- Ogni persona impiega un tempo differente per la propria operazione allo sportello. Una volta terminata l'operazione, la persona esce dall'ufficio.

Modellazione:
- l'ufficio viene modellato come una classe JAVA, in cui si attiva un ThreadPool di dimensione uguale al numero degli sportelli;
- la coda delle persone presenti nella prima sala è gestita esplicitamente dal programma;
- la seconda coda è quella gestita implicitamente dal ThreadPool;
- ogni persona viene modellata come un TASK che deve essere assegnato ad uno dei thread associati agli sportelli.

Si considerano due flussi: 
- 0 (prefissato): all'inizio del programma, tutti i clienti entrano e non se ne accettano altri;
- 1 (continuo): si prevede oltre al flusso continuo di clienti, la possibilità che l'operatore chiuda lo sportello stesso dopo che in un certo intervallo di tempo non si presentano clienti al suo sportello.

### Assegnamento 3: RepartoOrtopedico
Il programma simula il reparto di Ortopedia di un ospedale. Il reparto è gestito da una equipe di 10 medici. I pazienti che accedono al reparto devono essere visitati dai medici e sono distinti in base ad un codice di gravità/urgenza della prestazione:
- Codice rosso (priorità su tutti): hanno priorità su tutti e la loro visita richiede l’intervento esclusivo di tutti i medici dell’equipe;
- Codice giallo (priorità sul bianco): la visita richiede l’intervento esclusivo di un solo medico (i) perchè quel medico ha una particolare specializzazione;
- Codice bianco: la visita richiede l’intervento esclusivo di un qualsiasi medico.

Nessuna visita può essere interrotta. 
Il gestore del reparto deve coordinare il lavoro dei medici (i.e. l’accesso dei pazienti alle visite). Lo si implementa con Monitor e con Lock.

Il programma simula il comportamento dei pazienti e del gestore del reparto.
- Input: numero di pazienti in codice bianco, giallo, rosso ed attiva un thread per ogni paziente.
- ogni utente accede k volte al reparto per effettuare la visita, con k generato casualmente. Simulare l'intervallo di tempo che intercorre tra un accesso ed il successivo e l'intervallo di permanenza in visita mediante il metodo sleep.
- termina quando tutti i pazienti utenti hanno completato le visite in reparto.

### Assegnamento 4: FileCrawler
Il programma riceve in input un filepath che individua una directory D e stampa le informazioni del contenuto di quella directory e, ricorsivamente, di tutti i file contenuti nelle sottodirectory di D.

### Assegnamento 5: GestioneContiCorrente
Progettare un'applicazione che attiva un insieme di thread. Uno di essi legge dal file gli oggetti “conto corrente” e li passa, uno per volta, ai thread presenti in un thread pool.
Ogni thread calcola il numero di occorrenze di ogni possibile causale all'interno di quel conto corrente ed aggiorna un contatore globale.
Alla fine il programma stampa per ogni possibile causale il numero totale di occorrenze.

*Nota: Utilizzare NIO per l'interazione con il file e JSON per la serializzazione.*

### Assegnamento 6: HTTPFileTransfer
Scrivere un programma JAVA che implementi un server Http che gestisca richieste di trasferimento di file di diverso tipo (es. immagini jpeg, gif) provenienti da un browser web.

### Assegnamento 7: NIOEchoServer
Scrivere un programma echo server usando la libreria java NIO e, in particolare, il Selector e canali in modalità non bloccante, e un programma echo client, usando NIO.
La funzionalità fornita è realizzata usando UDP per la comunicazione tra client e server, invece del protocollo ICMP (Internet Control Message Protocol).
Inoltre, poichè l'esecuzione dei programmi avverrà su un solo host o sulla rete locale ed in entrambe i casi sia la latenza che la perdita di pacchetti risultano trascurabili, il server introduce un ritardo artificiale ed ignora alcune richieste per simulare la perdita di pacchetti.

### Assegnamento 8: JavaPinger
Implementare un server PING ed un corrispondente client PING che consenta al client di misurare il suo RTT verso il server.

### Assegnamento 9: GestioneCongresso
Si progetti un’applicazione Client/Server per la gestione delle registrazioni ad un congresso. L’organizzazione del congresso fornisce agli speaker delle varie sessioni un’interfaccia tramite la quale iscriversi ad una sessione, e la possibilità di visionare i programmi delle varie giornate del congresso, con gli interventi delle varie sessioni.

### Assegnamento 10: MulticastDateServer
Definire un Server TimeServer, che invia su un gruppo di multicast dategroup, ad intervalli regolari,la data e l’ora.
Attende tra un invio ed il successivo un intervallo di tempo simulata mediante il metodo sleep().
Definire un client TimeClient che si unisce a dategroup e riceve, per dieci volte consecutive, data ed ora, le visualizza, quindi termina.
