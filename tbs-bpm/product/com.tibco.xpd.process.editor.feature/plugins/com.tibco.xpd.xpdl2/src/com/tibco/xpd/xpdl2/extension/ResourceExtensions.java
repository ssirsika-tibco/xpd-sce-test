package com.tibco.xpd.xpdl2.extension;

import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.XMLSave;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.xml.sax.InputSource;

public class ResourceExtensions extends XMLResourceImpl {

    @Override
    public void doLoad(InputSource inputSource, Map<?, ?> options)
            throws IOException {
        IEditingDomainProvider existingAdapter =
                (IEditingDomainProvider) EcoreUtil.getRegisteredAdapter(this,
                        IEditingDomainProvider.class);

        InternalTransactionalEditingDomain domain =
                (InternalTransactionalEditingDomain) existingAdapter
                        .getEditingDomain();
        Transaction tx = null;
        if (domain != null) {
            tx = domain.getActiveTransaction();
            if (tx == null || tx.isReadOnly()) {
                try {
                    tx = domain.startTransaction(false, options);
                    super.doLoad(inputSource, options);
                } catch (InterruptedException e) {
                    throw new IOException(e.getMessage());
                } finally {
                    try {
                        tx.commit();
                    } catch (RollbackException e) {
                        throw new IOException(e.getMessage());
                    }
                }
            } else {
                super.doLoad(inputSource, options);
            }
        }
    }

    public ResourceExtensions() {
        super();
    }

    public ResourceExtensions(URI uri) {
        super(uri);
    }

    protected XMLHelper createXMLHelper() {
        return new HelperExtensions(this);
    }

    protected XMLLoad createXMLLoad() {
        return new LoadExtensions(createXMLHelper());
    }

    protected XMLSave createXMLSave() {
        return new SaveExtensions(createXMLHelper());
    }
}
