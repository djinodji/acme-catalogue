<?php

namespace AppBundle\Controller;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use AppBundle\Entity\Abonnement;
use AppBundle\Form\AbonnementType;

/**
 * Abonnement controller.
 *
 * @Route("/abonnement")
 */
class AbonnementController extends Controller
{
    /**
     * Lists all Abonnement entities.
     *
     * @Route("/", name="abonnement_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $abonnements = $em->getRepository('AppBundle:Abonnement')->findAll();

        return $this->render('abonnement/index.html.twig', array(
            'abonnements' => $abonnements,
        ));
    }

    /**
     * Creates a new Abonnement entity.
     *
     * @Route("/new", name="abonnement_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $abonnement = new Abonnement();
        $form = $this->createForm('AppBundle\Form\AbonnementType', $abonnement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($abonnement);
            $em->flush();

            return $this->redirectToRoute('abonnement_index', array('id' => $abonnement->getId()));
        }

        return $this->render('abonnement/new.html.twig', array(
            'abonnement' => $abonnement,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a Abonnement entity.
     *
     * @Route("/{id}", name="abonnement_show")
     * @Method("GET")
     */
    public function showAction(Abonnement $abonnement)
    {
        $deleteForm = $this->createDeleteForm($abonnement);

        return $this->render('abonnement/show.html.twig', array(
            'abonnement' => $abonnement,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing Abonnement entity.
     *
     * @Route("/{id}/edit", name="abonnement_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Abonnement $abonnement)
    {
        $deleteForm = $this->createDeleteForm($abonnement);
        $editForm = $this->createForm('AppBundle\Form\AbonnementType', $abonnement);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($abonnement);
            $em->flush();

            return $this->redirectToRoute('abonnement_edit', array('id' => $abonnement->getId()));
        }

        return $this->render('abonnement/edit.html.twig', array(
            'abonnement' => $abonnement,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a Abonnement entity.
     *
     * @Route("/{id}", name="abonnement_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, Abonnement $abonnement)
    {
        $form = $this->createDeleteForm($abonnement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($abonnement);
            $em->flush();
        }

        return $this->redirectToRoute('abonnement_index');
    }

    /**
     * Creates a form to delete a Abonnement entity.
     *
     * @param Abonnement $abonnement The Abonnement entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Abonnement $abonnement)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('abonnement_delete', array('id' => $abonnement->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
