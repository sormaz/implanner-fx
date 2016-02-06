/**
 * 
 */
package application;

/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This interface is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This interface notifies listener (DrawPanel) that layout has been modified.
 * @author Arif
 * @Date 20 Feb 2013
 * 
 */
public interface LayoutChangeListener {

	/**
	 * Method called when graph layout is changed
	 */
	public void layoutChanged();
	
}
