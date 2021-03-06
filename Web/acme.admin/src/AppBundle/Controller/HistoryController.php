<?php

namespace AppBundle\Controller;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use AppBundle\Entity\History;
use AppBundle\Form\HistoryType;

/**
 * History controller.
 *
 * @Route("/history")
 */
class HistoryController extends Controller
{
    /**
     * Lists all History entities.
     *
     * @Route("/", name="history_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $histories = $em->getRepository('AppBundle:History')->findAll();

        return $this->render('history/index.html.twig', array(
            'histories' => $histories,
        ));
    }

    /**
     * Creates a new History entity.
     *
     * @Route("/new", name="history_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $history = new History();
        $form = $this->createForm('AppBundle\Form\HistoryType', $history);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($history);
            $em->flush();

            return $this->redirectToRoute('history_show', array('id' => $history->getId()));
        }

        return $this->render('history/new.html.twig', array(
            'history' => $history,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a History entity.
     *
     * @Route("/{id}", name="history_show")
     * @Method("GET")
     */
    public function showAction(History $history)
    {
        $deleteForm = $this->createDeleteForm($history);

        return $this->render('history/show.html.twig', array(
            'history' => $history,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing History entity.
     *
     * @Route("/{id}/edit", name="history_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, History $history)
    {
        $deleteForm = $this->createDeleteForm($history);
        $editForm = $this->createForm('AppBundle\Form\HistoryType', $history);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($history);
            $em->flush();

            return $this->redirectToRoute('history_edit', array('id' => $history->getId()));
        }

        return $this->render('history/edit.html.twig', array(
            'history' => $history,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a History entity.
     *
     * @Route("/{id}", name="history_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, History $history)
    {
        $form = $this->createDeleteForm($history);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($history);
            $em->flush();
        }

        return $this->redirectToRoute('history_index');
    }

    /**
     * Creates a form to delete a History entity.
     *
     * @param History $history The History entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(History $history)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('history_delete', array('id' => $history->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
