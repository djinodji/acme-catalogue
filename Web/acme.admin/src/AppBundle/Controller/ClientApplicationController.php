<?php

namespace AppBundle\Controller;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use AppBundle\Entity\ClientApplication;
use AppBundle\Form\ClientApplicationType;

/**
 * ClientApplication controller.
 *
 * @Route("/clientapplication")
 */
class ClientApplicationController extends Controller
{
    /**
     * Lists all ClientApplication entities.
     *
     * @Route("/", name="clientapplication_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $clientApplications = $em->getRepository('AppBundle:ClientApplication')->findAll();

        return $this->render('clientapplication/index.html.twig', array(
            'clientApplications' => $clientApplications,
        ));
    }

    /**
     * Creates a new ClientApplication entity.
     *
     * @Route("/new", name="clientapplication_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $clientApplication = new ClientApplication();
        $form = $this->createForm('AppBundle\Form\ClientApplicationType', $clientApplication);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($clientApplication);
            $em->flush();

            return $this->redirectToRoute('clientapplication_show', array('id' => $clientApplication->getId()));
        }

        return $this->render('clientapplication/new.html.twig', array(
            'clientApplication' => $clientApplication,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a ClientApplication entity.
     *
     * @Route("/{id}", name="clientapplication_show")
     * @Method("GET")
     */
    public function showAction(ClientApplication $clientApplication)
    {
        $deleteForm = $this->createDeleteForm($clientApplication);

        return $this->render('clientapplication/show.html.twig', array(
            'clientApplication' => $clientApplication,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing ClientApplication entity.
     *
     * @Route("/{id}/edit", name="clientapplication_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, ClientApplication $clientApplication)
    {
        $deleteForm = $this->createDeleteForm($clientApplication);
        $editForm = $this->createForm('AppBundle\Form\ClientApplicationType', $clientApplication);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($clientApplication);
            $em->flush();

            return $this->redirectToRoute('clientapplication_edit', array('id' => $clientApplication->getId()));
        }

        return $this->render('clientapplication/edit.html.twig', array(
            'clientApplication' => $clientApplication,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a ClientApplication entity.
     *
     * @Route("/{id}", name="clientapplication_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, ClientApplication $clientApplication)
    {
        $form = $this->createDeleteForm($clientApplication);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($clientApplication);
            $em->flush();
        }

        return $this->redirectToRoute('clientapplication_index');
    }

    /**
     * Creates a form to delete a ClientApplication entity.
     *
     * @param ClientApplication $clientApplication The ClientApplication entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(ClientApplication $clientApplication)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('clientapplication_delete', array('id' => $clientApplication->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
