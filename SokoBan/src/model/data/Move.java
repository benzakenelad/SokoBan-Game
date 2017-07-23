package model.data;

import model.policy.Policy;

/**
 * <p>SokoBan move player interface </p>
 * @author Elad Ben Zaken
 *
 */
public interface Move {
	public void Action(Level lvl, Policy policy, String direction) throws Exception;
}
