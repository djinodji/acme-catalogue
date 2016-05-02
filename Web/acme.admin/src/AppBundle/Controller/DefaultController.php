<?php

namespace AppBundle\Controller;

use AppBundle\Entity\Article;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

class DefaultController extends Controller
{
    /**
     * @Route("/", name="home")
     */
    public function homeAction()
    {
        return $this->render('AppBundle:default:home.html.twig');
    }

    /**
     * @Route("/post/{id}", name="post")
     */
    public function postAction(Article $article)
    {

    }

    public function loginAction(Request $request)
    {

    }


}
