/** 
The Sample Gadget example is supplied "as is" with no warranties. The code in TestGadget is intended
as a simple illustration of the concepts and techniques needed to develop a custom gadget application.
It is not intended as a basis for production-ready code and should not be used as such. 
Any references to any third party software in the code is not under our control and we can offer no warranties
*/
package com.tibco.openspace.basegadget.client;

import com.tibco.bpm.web.client.model.types.WorkItem;
import com.tibco.forms.client.Form;
import com.tibco.forms.client.FormActionCallbackHandler;
import com.tibco.forms.client.FormLoaderCallbackHandler;
import com.tibco.forms.client.FormRunner;

public class FormRenderer
{
	private Form			openForm	= null;

	private IFormPresenter	presenter	= null;

	public FormRenderer(IFormPresenter aPresenter)
	{
		presenter = aPresenter;
	}

	public void changeFormLocale(String locale)
	{
		if (locale != null && openForm != null)
		{
			openForm.setLocale(locale);
		}
	}

	public void render(final WorkItem currentItem, final String aBomJSPath, final String aFormId, String locale)
	{
		Logger.trace("render called [" + System.currentTimeMillis() + "]");

		try
		{
			Logger.log("ID of panel to render into : " + aFormId);

			Logger.trace("FormRunner.loadForm called [" + System.currentTimeMillis() + "]");

			FormRunner.loadForm(currentItem.getFormURL(), currentItem.getDataFeed(), aBomJSPath, locale, aFormId,
					new FormLoaderCallbackHandler()
					{
						@Override
						public void onError(Throwable exception)
						{
							openForm = null;

							Logger.error("Failed to get the form definition", exception);

						}

						@Override
						public void onLoad(final Form form)
						{
							Logger.trace("FormRunner.loadForm completed [" + System.currentTimeMillis() + "]");

							openForm = form;

							form.setCallbackHandler(Form.ACTION_SUBMIT, new FormActionCallbackHandler()
							{
								@Override
								public void doExecute(String actionName, Form form)
								{
									try
									{
										openForm = null;

										currentItem.setDataFeed(form.getSerializedParameters());
									}
									catch (Exception e)
									{
										e.printStackTrace();
										Logger.log("Failed to handle the SUBMIT action", e);
									}
									presenter.itemCompleted(currentItem, form);
									form.destroy();

								}

								@Override
								public void doPostExecute(String actionName, Form form)
								{
									// TODO Auto-generated method stub

								}

								@Override
								public void doPreExecute(String actionName, Form form)
								{
									// TODO Auto-generated method stub

								}
							});

							form.setCallbackHandler(Form.ACTION_CLOSE, new FormActionCallbackHandler()
							{
								@Override
								public void doExecute(String actionName, Form form)
								{
									try
									{
										openForm = null;

										currentItem.setDataFeed(form.getSerializedParameters());
									}
									catch (Exception e)
									{
										e.printStackTrace();
										Logger.log("Failed to handle the CLOSE action", e);
									}

									presenter.itemClosed(currentItem, form);
									form.destroy();

								}

								@Override
								public void doPostExecute(String actionName, Form form)
								{
									// TODO Auto-generated method stub

								}

								@Override
								public void doPreExecute(String actionName, Form form)
								{
									// TODO Auto-generated method stub

								}
							});

							form.setCallbackHandler(Form.ACTION_CANCEL, new FormActionCallbackHandler()
							{
								@Override
								public void doExecute(String actionName, Form form)
								{
									openForm = null;

									presenter.itemCancelled(currentItem, form);
									form.destroy();
								}

								@Override
								public void doPostExecute(String actionName, Form form)
								{
									// TODO Auto-generated method stub

								}

								@Override
								public void doPreExecute(String actionName, Form form)
								{
									// TODO Auto-generated method stub

								}
							});
						}
					}, false);
		}
		catch (Exception e)
		{
			Logger.error("error occurred loading form", e);
		}
	}

}