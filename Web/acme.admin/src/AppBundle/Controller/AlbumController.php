<?php

namespace AppBundle\Controller;

use AppBundle\Entity\Utilisateur;
use DateTime;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use AppBundle\Entity\Album;
use AppBundle\Form\AlbumType;

/**
 * Album controller.
 *
 * @Route("/album")
 */
class AlbumController extends Controller
{
    /**
     * Lists all Album entities.
     *
     * @Route("/", name="album_index")
     * @Method("GET")
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();

        $albums = $em->getRepository('AppBundle:Album')->findAll();

        return $this->render('album/index.html.twig', array(
            'albums' => $albums,
        ));
    }

    /**
     * Creates a new Album entity.
     *
     * @Route("/new", name="album_new")
     * @Method({"GET", "POST"})
     */
    public function newAction(Request $request)
    {
        $album = new Album();
        $album->addUtilisateur(new Utilisateur());
        $form = $this->createForm('AppBundle\Form\AlbumType', $album);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $em = $this->getDoctrine()->getManager();
            $em->merge($album);
            $em->flush();

            return $this->redirectToRoute('album_index');
        }

        return $this->render('album/new.html.twig', array(
            'album' => $album,
            'form' => $form->createView(),
        ));
    }

    /**
     * Finds and displays a Album entity.
     *
     * @Route("/{id}", name="album_show")
     * @Method("GET")
     */
    public function showAction(Album $album)
    {
        $deleteForm = $this->createDeleteForm($album);

        return $this->render('album/show.html.twig', array(
            'album' => $album,
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing Album entity.
     *
     * @Route("/{id}/edit", name="album_edit")
     * @Method({"GET", "POST"})
     */
    public function editAction(Request $request, Album $album)
    {
        $deleteForm = $this->createDeleteForm($album);
        $editForm = $this->createForm('AppBundle\Form\AlbumType', $album);
        $editForm->handleRequest($request);

        if ($editForm->isSubmitted() && $editForm->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $album->setLastUpdateTime(new DateTime());
            $em->persist($album);
            $em->flush();

            return $this->redirectToRoute('album_edit', array('id' => $album->getId()));
        }

        return $this->render('album/edit.html.twig', array(
            'album' => $album,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Deletes a Album entity.
     *
     * @Route("/{id}", name="album_delete")
     * @Method("DELETE")
     */
    public function deleteAction(Request $request, Album $album)
    {
        $form = $this->createDeleteForm($album);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($album);
            $em->flush();
        }

        return $this->redirectToRoute('album_index');
    }

    /**
     * Creates a form to delete a Album entity.
     *
     * @param Album $album The Album entity
     *
     * @return \Symfony\Component\Form\Form The form
     */
    private function createDeleteForm(Album $album)
    {
        return $this->createFormBuilder()
            ->setAction($this->generateUrl('album_delete', array('id' => $album->getId())))
            ->setMethod('DELETE')
            ->getForm()
        ;
    }
}
