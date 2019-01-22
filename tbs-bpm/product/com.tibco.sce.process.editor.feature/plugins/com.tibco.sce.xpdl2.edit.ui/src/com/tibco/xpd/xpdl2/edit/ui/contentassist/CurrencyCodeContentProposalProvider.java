package com.tibco.xpd.xpdl2.edit.ui.contentassist;

import java.util.ArrayList;

import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;

/**
 * Used to get all proposals for the different currency codes available for each locale.
 * @author glewis
 *
 */
public class CurrencyCodeContentProposalProvider implements IContentProposalProvider{
    
    private String[] data=null;

    /**
     * @param proposals
     */
    public CurrencyCodeContentProposalProvider(String[] proposals) {
         data = proposals;
    }
   
    /**
     * @see org.eclipse.jface.fieldassist.IContentProposalProvider#getProposals(java.lang.String, int)
     *
     * @param contents
     * @param position
     * @return
     */
    public IContentProposal[] getProposals(String contents, int position) {
        ArrayList<IContentProposal> newProposals = new ArrayList<IContentProposal>();
        
        //add the proposals depending on if they start with the control text
        for (int i=0;i<data.length;i++){
            final String proposalValue = data[i];            
            if (proposalValue != null && data[i].toLowerCase().startsWith(contents.toLowerCase())){
                IContentProposal proposal = new IContentProposal() {
                    public String getContent() {
                        return proposalValue;
                    }
                    public String getDescription() {
                        return null;
                    }
                    public String getLabel() {
                        return null;
                    }
                    public int getCursorPosition() {
                        return proposalValue.length();
                    }
                };                
                newProposals.add(proposal);
            }
        }
        
        IContentProposal[] cpArray = new IContentProposal[newProposals.size()];
        return (IContentProposal[])newProposals.toArray(cpArray);        
    }
    
    

}
