# RO_graphe_VV

Ajout des éléments théorique nouveau par semaine. 

Copie en profondeur --> mieux d'utiliser la sérialisation binaire pour faire une copie
--> code de la copie par constructeur dans lequel on passe un node:
  public Node(Node n){
        this.name = n.getName();
        this.visited = n.isVisited();
        this.level = n.getLevel();
        this.djikstraWeight = n.getDjikstraWeight();
        this.predecessor = n.getPredecessor();
        this.tableVPCC = n.getTableVPCC();
        this.exitingEdges = n.getExitingEdges(); // attention il faut aussi copier l'array avec new Array etc...ArrayCopyOf
        this.incomingEdges = n.getIncomingEdges();
        }
