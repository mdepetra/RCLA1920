# RCLA1920

Assegnamenti per il Modulo di Laboratorio del corso di Reti di Calcolatori - A (2019/2020)

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
Scrivere un programma echo server usando la libreria java NIO e, in particolare, il Selector e canali in modalità non bloccante, e un programma echo client, usando NIO.
La funzionalità fornita è realizzata usando UDP per la comunicazione tra client e server, invece del protocollo ICMP (Internet Control Message Protocol).
Inoltre, poichè l'esecuzione dei programmi avverrà su un solo host o sulla rete locale ed in entrambe i casi sia la latenza che la perdita di pacchetti risultano trascurabili, il server introduce un ritardo artificiale ed ignora alcune richieste per simulare la perdita di pacchetti.

### Assegnamento 8: JavaPinger
Implementare un server PING ed un corrispondente client PING che consenta al client di misurare il suo RTT verso il server.

### Assegnamento 9: GestioneCongresso
Si progetti un’applicazione Client/Server per la gestione delle registrazioni ad un congresso. L’organizzazione del congresso fornisce agli speaker delle varie sessioni un’interfaccia tramite la quale iscriversi ad una sessione, e la possibilità di visionare i programmi delle varie giornate del congresso, con gli interventi delle varie sessioni.

### Assegnamento 10: MulticastDateServer
Definire un Server TimeServer, che invia su un gruppo di multicast dategroup, ad intervalli regolari,la data e l’ora.
Attende tra un invio ed il successivo un intervallo di tempo simulata mediante il metodo sleep().
Definire un client TimeClient che si unisce a dategroup e riceve, per dieci volte consecutive, data ed ora, le visualizza, quindi termina.
