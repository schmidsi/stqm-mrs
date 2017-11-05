package ch.fhnw.swc.mrs.datagenerator;

/**
 * Load data into MRS database.
 */
public final class Main {

    /**
     * Start the loader.
     * @param args arguments
     * @throws Exception whenever something goes wrong.
     */
	public static void main(String[] args) throws Exception {
	    HsqlDatabase db = new HsqlDatabase();
	    Dataloader loader = new GeneratingDataloader(50000, 100000, 5000);
	    loader.load(db.getConnection());
	    System.out.println("Datagenerator: done");
	}
	
	/**
	 * Prevent instantiation.
	 */
	private Main() { }

}
