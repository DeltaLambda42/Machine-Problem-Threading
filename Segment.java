public class Segment extends Thread {
  private String currentRow;
  private int start;
  private int length;
  private String result;
  
  public Segment(String currentRow, int start, int length) {
    this.currentRow = currentRow;
    this.start = start;
    this.length = length;
    result = "";
  }
  
  public void run() {
    String condition;
    for(int i = start; i < start + length; i++) {
      if(i == 0) {
        condition = "0".concat(currentRow.substring(0, 2));
      } else if(i == currentRow.length() - 1) {
        condition = currentRow.substring(currentRow.length() - 2, currentRow.length()).concat("0");
      } else {
        condition = currentRow.substring(i - 1, i + 2);
      }
      result = result.concat(Rule30.ruleResult(condition));
    }
  }
  
  public String getResult() {
    return result;
  }
}