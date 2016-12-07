import java.util.*;

class Rule {
  String condition;
  String result;

  Rule(String condition, String result) {
  this.condition = condition;
  this.result = result;
 }
}

//no Comments whatsoever

public class Rule30 {
  final static Rule ONE = new Rule("111", "0");
  final static Rule TWO = new Rule("110", "0");
  final static Rule THREE = new Rule("101", "0");
  final static Rule FOUR = new Rule("100", "1");
  final static Rule FIVE = new Rule("011", "1");
  final static Rule SIX = new Rule("010", "1");
  final static Rule SEVEN = new Rule("001", "1");
  final static Rule EIGHT = new Rule("000", "0");

  final static int N_THREADS = 10;

  final static Rule[] rules = {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT};

  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    Rule30.display(n);
  }

  static String ruleResult(String ruleCondition) {
    for(int i = 0; i < rules.length; i++) {
      if(rules[i].condition.equals(ruleCondition)) {
        return rules[i].result;
      }
    }
    return "";
  }

  static void display(int gridSize) {
    String condition;
    String curRow = "";
    String nextRow = "";
    int root = gridSize / 2;

    for(int i = 0; i < gridSize; i++) {
      if(i == root) {
        curRow += "1";
      } else {
        curRow += "0";
      }
    }
    System.out.println(curRow);
    int sectsNumber;
    if(gridSize < N_THREADS) {
      sectsNumber = gridSize;
    } else {
      sectsNumber = N_THREADS;
    }

    Segment[] sects = new Segment[sectsNumber];
    int sectsPerThread = gridSize / sectsNumber;
    int sectsWithAdditional = gridSize % sectsNumber;

    for(int k = 1; k < gridSize; k++) {
      int start = 0;
      for(int i = 0; i < sectsNumber; i++) {
        if(i < sectsWithAdditional) {
          sects[i] = new Segment(curRow, start, sectsPerThread+1);
          start = start + sectsPerThread + 1;
        } else {
          sects[i] = new Segment(curRow, start, sectsPerThread);
          start = start + sectsPerThread;
        }
      }

      for(int i = 0; i < sectsNumber; i++) {
        sects[i].start();
      }

      for(int i = 0; i < sectsNumber; i++) {
        while(sects[i].isAlive()) {
          try {
            sects[i].join();
          } catch(InterruptedException e) {
          }
        }
      }

      for(int i = 0; i < sectsNumber; i++) {
        nextRow = nextRow + sects[i].getResult();
      }
      System.out.println(nextRow);
      curRow = nextRow;
      nextRow = "";
    }
  }
}
